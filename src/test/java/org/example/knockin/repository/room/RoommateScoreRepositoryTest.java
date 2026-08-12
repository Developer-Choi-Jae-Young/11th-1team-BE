package org.example.knockin.repository.room;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.example.knockin.config.QueryDslConfig;
import org.example.knockin.entity.auth.LoginProviderType;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingRequiredStatus;
import org.example.knockin.entity.chat.ChattingRoom;
import org.example.knockin.entity.life.*;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberRole;
import org.example.knockin.entity.room.MyRoommate;
import org.example.knockin.entity.room.RoommateMatchingRequired;
import org.example.knockin.entity.room.RoommateRequiredStatus;
import org.example.knockin.entity.room.RoommateScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(QueryDslConfig.class)
@DisplayName("룸메이트 점수 리포지토리")
class RoommateScoreRepositoryTest {

    @Autowired
    private RoommateScoreRepository roommateScoreRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("내 룸메이트 점수 조회는 로그 차수의 회원으로 평가자 방향을 판별한다")
    void findOneByMyRoommateIdAndMemberIdReturnsEvaluatorDirection() {
        // Given
        Member evaluator = persistMember("score-evaluator");
        Member target = persistMember("score-target");
        MyRoommate myRoommate = persistMyRoommate(evaluator, target);
        LifePattern lifePattern = persistLifePattern("청결 민감도", 1);
        LifePatternInformation information = persistLifePatternInformation(lifePattern, "3");
        MemberLifePatternLogDegree evaluatorDegree = persistMemberLifePatternLogDegree(1L);
        MemberLifePatternLogDegree targetDegree = persistMemberLifePatternLogDegree(1L);
        persistMemberLifePatternLog(evaluator, information, evaluatorDegree);
        persistMemberLifePatternLog(evaluator, information, evaluatorDegree);
        persistMemberLifePatternLog(target, information, targetDegree);
        persistRoommateScore(myRoommate, evaluatorDegree, null, 80);
        persistRoommateScore(myRoommate, targetDegree, null, 20);
        entityManager.flush();
        entityManager.clear();

        // When
        Optional<RoommateScore> score = roommateScoreRepository.findOneByMyRoommateIdAndMemberId(
                myRoommate.getId(),
                evaluator.getId()
        );

        // Then
        assertThat(score).isPresent().get().extracting(RoommateScore::getScore).isEqualTo(80);
    }

    private Member persistMember(String providerId) {
        Member member = Member.builder()
                .providerType(LoginProviderType.KAKAO)
                .providerId(providerId)
                .role(MemberRole.USER)
                .isDelete(false)
                .build();
        entityManager.persist(member);
        return member;
    }

    private MyRoommate persistMyRoommate(Member requester, Member requestee) {
        ChattingRequired chattingRequired = ChattingRequired.builder()
                .requester(requester)
                .requestee(requestee)
                .status(ChattingRequiredStatus.ACCEPTED)
                .build();
        entityManager.persist(chattingRequired);

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .chattingRequired(chattingRequired)
                .build();
        entityManager.persist(chattingRoom);

        RoommateMatchingRequired matchingRequired = RoommateMatchingRequired.builder()
                .requester(requester)
                .requestee(requestee)
                .chattingRoom(chattingRoom)
                .status(RoommateRequiredStatus.ACCEPTED)
                .build();
        entityManager.persist(matchingRequired);

        MyRoommate myRoommate = MyRoommate.builder()
                .roommateMatchingRequired(matchingRequired)
                .isDeleted(false)
                .build();
        entityManager.persist(myRoommate);
        return myRoommate;
    }

    private LifePattern persistLifePattern(String name, Integer sort) {
        LifePattern lifePattern = LifePattern.builder()
                .name(name)
                .dtype(LifePatternType.SCALE)
                .isDeleted(false)
                .sort(sort)
                .lifePatternDescription("생활패턴 설명")
                .preferenceDescription("선호조건 설명")
                .build();
        entityManager.persist(lifePattern);
        return lifePattern;
    }

    private LifePatternInformation persistLifePatternInformation(LifePattern lifePattern, String value) {
        LifePatternInformation information = LifePatternInformation.builder()
                .lifePattern(lifePattern)
                .dvalue(value)
                .description(value)
                .build();
        entityManager.persist(information);
        return information;
    }

    private MemberLifePatternLogDegree persistMemberLifePatternLogDegree(Long degree) {
        MemberLifePatternLogDegree logDegree = MemberLifePatternLogDegree.builder().degree(degree).build();
        entityManager.persist(logDegree);
        return logDegree;
    }

    private MemberLifePatternLog persistMemberLifePatternLog(
            Member member,
            LifePatternInformation information,
            MemberLifePatternLogDegree logDegree
    ) {
        MemberLifePatternLog log = MemberLifePatternLog.builder()
                .member(member)
                .lifePatternInformation(information)
                .memberLifePatternLogDegree(logDegree)
                .build();
        entityManager.persist(log);
        return log;
    }

    private PreferenceConditionWeightLog persistPreferenceConditionWeightLog(Member member, LifePattern lifePattern) {
        PreferenceConditionWeightLog log = PreferenceConditionWeightLog.builder()
                .member(member)
                .lifePattern(lifePattern)
                .build();
        entityManager.persist(log);
        return log;
    }

    private void persistRoommateScore(
            MyRoommate myRoommate,
            MemberLifePatternLogDegree memberLifePatternLogDegree,
            PreferenceConditionWeightLogDegree preferenceConditionWeightLogDegree,
            Integer score
    ) {
        RoommateScore roommateScore = RoommateScore.builder()
                .myRoommate(myRoommate)
                .memberLifePatternLogDegree(memberLifePatternLogDegree)
                .preferenceConditionWeightLogDegree(preferenceConditionWeightLogDegree)
                .score(score)
                .build();
        entityManager.persist(roommateScore);
    }

}
