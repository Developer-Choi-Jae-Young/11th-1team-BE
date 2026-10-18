# 룸메이트 매칭 조회: 차단 서브쿼리의 OR 조건 분리

## 요약

`GET /roommate/matches`, `GET /roommate/boards`의 차단 관계 조회에서 발생한 slow query는 **양방향 차단 조건을 OR로 묶은 상관 서브쿼리가 `block` 테이블을 전체 스캔하는 문제**였다.

차단 방향별로 `NOT EXISTS` 서브쿼리를 분리하고 두 조건을 AND로 연결하자, 기존 FK 컬럼 인덱스를 활용해 문제를 해결할 수 있었다. 이번 개선은 **기존 인덱스를 활용하도록 조건식을 바꾼 쿼리 튜닝**으로 마무리했다. 추가 복합 인덱스 설계는 필요하지 않았다.

## 1. 원인: 양방향 차단 조건을 묶은 OR 서브쿼리

매칭 목록에서는 다음 두 관계의 회원을 모두 제외해야 한다.

- 요청자가 차단한 회원
- 요청자를 차단한 회원

기존 쿼리는 두 방향을 하나의 `NOT EXISTS` 안에서 OR로 묶었다. 아래에서 `20005`는 요청자 ID, `m.id`는 조회 중인 후보 회원 ID다.

```sql
AND NOT EXISTS (
    SELECT 1
    FROM block b
    WHERE b.is_deleted = FALSE
      AND (
          (b.blocker_id = 20005 AND b.blocked_id = m.id)
          OR
          (b.blocker_id = m.id AND b.blocked_id = 20005)
      )
)
```

기존 H2 실행계획에서 차단 서브쿼리는 다음과 같이 나타났다.

```sql
FROM PUBLIC.BLOCK B
    /* PUBLIC.BLOCK.tableScan */
    /* scanCount: 10002 */
```

FK 컬럼에 인덱스가 있었지만, 이 실행계획에서는 OR로 결합된 양방향 조건을 처리할 때 해당 인덱스를 활용하지 못하고 전체 스캔을 선택했다. 바깥 쿼리의 후보 회원을 참조하는 상관 서브쿼리여서 차단 관계를 반복 탐색하는 비용이 병목이 됐다.

이 설명은 이번 쿼리와 실행계획에 대한 분석이다. OR 조건이 있는 모든 쿼리가 전체 스캔을 수행한다는 의미는 아니다. 또한 H2의 `scanCount`는 실행기의 탐색 횟수 통계로, 물리적인 디스크 I/O 횟수나 전체 API 실행시간을 뜻하지 않는다.

## 2. 해결: 두 개의 NOT EXISTS를 AND로 연결

차단 방향을 나누어 각 서브쿼리가 AND로 연결된 동등 조건만 검사하도록 변경했다.

```sql
-- 요청자가 후보 회원을 차단하지 않았는지 확인
AND NOT EXISTS (
    SELECT 1
    FROM block b
    WHERE b.blocker_id = 20005
      AND b.blocked_id = m.id
      AND b.is_deleted = FALSE
)
-- 후보 회원이 요청자를 차단하지 않았는지 확인
AND NOT EXISTS (
    SELECT 1
    FROM block b
    WHERE b.blocked_id = 20005
      AND b.blocker_id = m.id
      AND b.is_deleted = FALSE
)
```

각 서브쿼리는 기존 `blocker_id` 또는 `blocked_id`의 FK 컬럼 인덱스로 탐색 범위를 좁히고, 나머지 회원 ID와 삭제 여부를 검사할 수 있다. 추가 확인 결과, 이 변경만으로 기존 인덱스를 활용해 차단 조회의 slow query를 해결할 수 있었다.

### 양방향 차단 제외 의미 유지

변경한 논리는 다음과 같다.

```text
변경 전: NOT EXISTS (유효한 정방향 차단 OR 유효한 역방향 차단)
변경 후: NOT EXISTS (유효한 정방향 차단)
         AND NOT EXISTS (유효한 역방향 차단)
```

어느 방향으로든 활성 차단 관계가 있으면 후보에서 제외하고, 삭제된 차단 관계는 무시한다. **기존 서브쿼리 안의 OR만 AND로 치환하면 두 방향이 동시에 성립하는 관계를 찾게 되므로 조회 의미가 달라진다.** 방향별 존재 여부를 나누고 바깥에서 AND로 연결하는 것이 핵심이다.

## 3. 튜닝 후 실행계획: 기존 FK 인덱스 활용 확인

추가로 확인한 H2 실행계획에서는 AND로 연결한 두 차단 서브쿼리가 모두 기존 FK 컬럼 인덱스를 사용했다. **차단용 복합 인덱스를 추가하지 않고도 조건식 변경으로 인덱스 탐색이 가능해진 사례**다.

첨부 계획의 조회 대상은 `ROOMMATE_BOARD` 목록이다. 앞서 설명한 회원 매칭 조회와 동일한 양방향 차단 제외 패턴을 사용하며, `RoommateBoardRepositoryImpl.notBlockedBetween()`에도 두 `NOT EXISTS`를 AND로 연결한 구현이 있다. 아래에서 요청자 ID는 `5`, 후보 회원 ID는 게시글 작성자인 `RB1_0.MEMBER_ID`다.

### 실제 차단 서브쿼리 실행계획

첨부 실행계획의 최종 WHERE 절에서 차단 조건을 발췌했다.

```sql
AND (NOT EXISTS(    SELECT
    1
FROM "PUBLIC"."BLOCK" "B1_0"
    /* PUBLIC.FKS7Y2QYKJFJ3YTFOEWWJWJAQ7T_INDEX_3: BLOCKED_ID = CAST(5 AS BIGINT) */
    /* scanCount: 1 */
WHERE ("B1_0"."BLOCKER_ID" = "RB1_0"."MEMBER_ID")
    AND ("B1_0"."IS_DELETED" = FALSE)
    AND ("B1_0"."BLOCKED_ID" = CAST(5 AS BIGINT))))
AND (NOT EXISTS(    SELECT
    1
FROM "PUBLIC"."BLOCK" "B4_0"
    /* PUBLIC.FKS7Y2QYKJFJ3YTFOEWWJWJAQ7T_INDEX_3: BLOCKED_ID = RB1_0.MEMBER_ID */
    /* scanCount: 1 */
WHERE ("B4_0"."BLOCKED_ID" = "RB1_0"."MEMBER_ID")
    AND ("B4_0"."IS_DELETED" = FALSE)
    AND ("B4_0"."BLOCKER_ID" = CAST(5 AS BIGINT))))
```

| 서브쿼리 | 확인하는 차단 방향 | 인덱스 탐색 조건 | 표시된 `scanCount` |
| --- | --- | --- | --- |
| `B1_0` | 게시글 작성자 → 요청자 | `BLOCKED_ID = 5` | 1 |
| `B4_0` | 요청자 → 게시글 작성자 | `BLOCKED_ID = RB1_0.MEMBER_ID` | 1 |

두 서브쿼리 모두 `FKS7Y2QYKJFJ3YTFOEWWJWJAQ7T_INDEX_3`를 사용한다. 이 계획에서는 **양쪽 모두 `blocked_id` 인덱스로 탐색**하고, `blocker_id`와 `is_deleted`는 나머지 조건으로 검사한다. 방향별로 서로 다른 FK 인덱스를 사용하거나 세 컬럼의 복합 인덱스를 사용한 계획이 아니다.

기존 OR 조건의 예시에서는 `BLOCK.tableScan`이 표시됐지만, 이번 AND 분리 사례에서는 FK 인덱스 탐색과 각 서브쿼리의 `scanCount: 1`을 확인할 수 있다. 이는 차단 조회에서 기존 인덱스가 활용됐다는 근거다. 다만 서로 다른 조회의 실행계획이므로 앞의 `scanCount: 10002`와 직접 비교해 성능이 10,002배 개선됐다고 해석하지 않는다. `scanCount: 1` 역시 전체 API에서 차단 데이터를 총 한 번만 읽었다는 뜻은 아니다.

### 추가 인덱스 없이 해결했다는 의미

첨부 계획의 바깥 게시글 조회에는 다음 인덱스가 사용되고 있다.

```sql
FROM "PUBLIC"."ROOMMATE_BOARD" "RB1_0"
    /* PUBLIC.IDX_ROOMMATE_BOARD_DELETED_CREATED_ID: IS_DELETED = FALSE */
    -- 중간 조건 생략
    /* scanCount: 1003 */
```

따라서 이 사례에서 추가 인덱스 없이 해결했다는 것은 **차단 서브쿼리를 위해 새 복합 인덱스를 만들 필요가 없었다는 의미**다. 조회 전체에 인덱스가 없었다는 의미는 아니다. 게시글 조회용 인덱스의 효과와 차단 조건 재작성의 효과는 구분해서 해석한다.

또한 첨부 쿼리에는 제목·지역 검색과 입주일 조건의 OR가 남아 있지만, 두 차단 서브쿼리는 인덱스를 사용한다. 이번 튜닝은 차단 관계를 확인하는 상관 서브쿼리 내부의 OR 결합을 변경한 것이다.

## 4. QueryDSL 적용

`MemberRepositoryImpl.findMatchingBasicRow()`는 WHERE 절에서 `notBlockedBetween(requesterId)`를 호출한다. 현재 구현은 다음과 같다.

```java
private BooleanExpression notBlockedBetween(@Nullable Long requesterId) {
    if (requesterId == null) {
        return null;
    }

    BooleanExpression requesterBlock = JPAExpressions
            .selectOne()
            .from(block)
            .where(
                    block.isDeleted.isFalse(),
                    block.blocker.id.eq(requesterId),
                    block.blocked.id.eq(member.id)
            )
            .notExists();

    BooleanExpression requesterBlocked = JPAExpressions
            .selectOne()
            .from(block)
            .where(
                    block.isDeleted.isFalse(),
                    block.blocked.id.eq(requesterId),
                    block.blocker.id.eq(member.id)
            )
            .notExists();

    return requesterBlocked.and(requesterBlock);
}
```

QueryDSL의 `.where(...)`에 전달한 조건들은 AND로 결합된다. 요청자 ID가 없는 경우에는 기존과 같이 차단 조건을 적용하지 않는다.

기존 `MemberRepositoryInterestFilterTest.findMatchingBasicRowExcludesBlockedMembersInBothDirections()`는 다음 조회 의미를 검증한다.

- 요청자가 차단한 회원과 요청자를 차단한 회원은 제외한다.
- 삭제된 차단 관계의 회원과 차단 관계가 없는 회원은 포함한다.

이 테스트는 결과의 정확성을 검증하며, 실행계획이나 성능을 검증하는 테스트는 아니다.

## 5. 확인 결과와 측정 기록

| 항목 | 변경 전 | 변경 후 |
| --- | --- | --- |
| 차단 조건 | 하나의 서브쿼리 안에서 양방향 조건을 OR로 결합 | 방향별 `NOT EXISTS`를 AND로 결합 |
| 차단 관계 탐색 | `block` 전체 스캔 | 기존 FK 컬럼 인덱스 활용 |
| 양방향 차단 제외 | 적용 | 동일하게 적용 |
| 추가 인덱스 | 기존 FK 컬럼 인덱스 존재 | 추가 생성 없이 해결 |

변경 후 인덱스 활용과 slow query 해결은 추가 확인된 결과를 반영한 것이다. 3절에는 같은 차단 조건 재작성을 적용한 게시글 목록 조회의 실행계획을 발췌해 인덱스명과 `scanCount`를 기록했다. 회원 매칭 조회와 동일 조건의 변경 전후 실행계획·성능 수치가 모두 있는 것은 아니므로, 이 자료로 API 응답시간이나 처리량의 개선율을 산출하지 않는다.

기존 문서에 있던 nGrinder 측정값은 참고 기록으로 유지한다.

| 항목 | 측정값 |
| --- | --- |
| 가상 사용자 수 | 10 VU |
| 평균 응답시간 | 411.58ms |
| 처리량 | 24.81 TPS |
| 오류 수 | 0 |

이 값은 차단 조건을 두 개의 `NOT EXISTS`로 분리한 뒤 측정한 **API 전체 결과**다. 기존 OR 쿼리의 실행시간이나 인덱스 추가 전후의 비교값으로 사용하지 않는다.

## 결론

이번 문제는 차단 서브쿼리의 조건 구조를 변경해 기존 FK 컬럼 인덱스를 활용하도록 하면서 해결했다. 이에 따라 기존 문서에서 제안했던 `(blocker_id, blocked_id, is_deleted)` 복합 인덱스 추가안은 이번 개선 범위에서 제외한다.

이 사례의 개선 범위는 **OR로 묶인 양방향 차단 조건을 두 개의 `NOT EXISTS`와 AND로 재작성한 것**이다. 인덱스 추가에 따른 저장 공간과 쓰기 유지 비용을 늘리지 않고 쿼리 튜닝으로 마무리했다.
