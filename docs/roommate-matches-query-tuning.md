# 룸메이트 매칭 조회: 차단 관계 조회 인덱스

## 검사 대상 선정 이유

`GET /roommate/matches`는 nGrinder 부하테스트에서 평균 응답시간 **411.58ms(약 411ms)**, 처리량 **24.81 TPS**를 기록해 쿼리 개선 대상으로 선정했다.

| 항목 | 측정값 |
| --- | --- |
| 가상 사용자 수 | 10 VU |
| 평균 응답시간 | 411.58ms |
| 처리량 | 24.81 TPS |
| 오류 수 | 0 |

이 수치는 이전 테스트에서 차단 조건을 두 개의 `NOT EXISTS`로 분리한 후 측정한 API 결과다. 아래에 제시한 기존 OR 쿼리의 실행시간이나, 이번 복합 인덱스의 적용 전후 비교 결과를 뜻하지 않는다.

## 실행계획에서 확인한 문제

제공된 H2 실행계획에서 기존 쿼리는 회원별로 양방향 차단 관계를 확인한다.

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

실행계획의 주요 부분은 다음과 같다.

```sql
FROM PUBLIC.MEMBER M
    /* PUBLIC.MEMBER.tableScan */
    /* scanCount: 20006 */

-- 회원별 차단 관계 확인 서브쿼리
FROM PUBLIC.BLOCK B
    /* PUBLIC.BLOCK.tableScan */
    /* scanCount: 10002 */
```

**차단 관계 확인에 BLOCK 테이블 전체 스캔을 사용하고 있었다.** 바깥 회원의 `m.id`를 참조하는 상관 서브쿼리이므로, 후보 회원을 처리하면서 차단 데이터를 반복 탐색하는 비용이 커질 수 있다. 이를 줄이기 위해 회원 쌍과 삭제 여부를 함께 탐색할 복합 인덱스를 선정했다.

`scanCount`는 H2 실행기의 탐색 횟수 통계이며 물리적인 디스크 블록 I/O 횟수가 아니다. 위 두 값을 곱해 실제 전체 스캔 횟수로 단정하지 않는다. 실행계획만으로 전체 API 지연 중 이 부분이 차지하는 시간까지 확정할 수도 없다.

## 추가할 인덱스 SQL

```sql
CREATE INDEX idx_block_pair_deleted
ON block (blocker_id, blocked_id, is_deleted);
```

### 이 컬럼 조합을 선택한 이유

| 컬럼 | 조회에서의 역할 |
| --- | --- |
| `blocker_id` | 차단한 회원을 지정한다. |
| `blocked_id` | 차단당한 회원을 지정한다. |
| `is_deleted` | 삭제되지 않은 차단 관계만 확인한다. |

회원 쌍을 먼저 특정하고 해당 관계의 삭제 여부까지 같은 인덱스 키에 포함한다. `blocker_id`만으로 탐색한 뒤 그 회원의 여러 차단 관계를 추가 검사하는 것보다, 세 동등 조건으로 탐색 범위를 좁힐 수 있도록 하는 목적이다. 실제 효과는 기존 인덱스 구성과 회원별 차단 데이터 수에 따라 달라진다.

양방향 조회에서도 동일한 인덱스를 사용할 수 있다. 두 방향 모두 `blocker_id`, `blocked_id`, `is_deleted`를 `=` 조건으로 지정하며, 컬럼에 대입하는 회원 ID만 서로 바뀌기 때문이다. 이 조회만을 위해 컬럼 순서를 뒤집은 인덱스를 추가할 필요는 없다.

## OR 조건 분리와 함께 적용하는 이유

인덱스를 생성해도 기존 OR 조건에서 H2가 해당 인덱스를 사용할지는 별도로 확인해야 한다. 제공된 실행계획은 전체 스캔을 보여 주지만, 이것만으로 인덱스 부재 또는 옵티마이저 제약 중 하나를 유일한 원인으로 확정할 수는 없다.

현재 브랜치의 `MemberRepositoryImpl`에는 차단 조건을 다음과 같이 분리한 변경이 있다. 각 서브쿼리가 세 동등 조건으로 회원 쌍을 탐색할 수 있도록 한다.

```sql
AND NOT EXISTS (
    SELECT 1
    FROM block b
    WHERE b.blocker_id = 20005
      AND b.blocked_id = m.id
      AND b.is_deleted = FALSE
)
AND NOT EXISTS (
    SELECT 1
    FROM block b
    WHERE b.blocker_id = m.id
      AND b.blocked_id = 20005
      AND b.is_deleted = FALSE
)
```

두 방향 중 어느 쪽으로도 유효한 차단 관계가 없어야 한다는 기존 의미는 유지된다. 쿼리 분리는 인덱스 탐색 조건을 단순하게 만들고, 복합 인덱스는 각 탐색에서 확인할 범위를 줄이는 역할을 한다.