package org.example.knockin.repository.room.Impl;

import static org.example.knockin.entity.room.QRoommateScore.roommateScore;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.QMemberLifePatternLog;
import org.example.knockin.entity.room.RoommateScore;
import org.example.knockin.repository.room.RoommateScoreRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoommateScoreRepositoryImpl implements RoommateScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<RoommateScore> findOneByMyRoommateIdAndMemberId(Long myRoommateId, Long memberId) {
        QMemberLifePatternLog evaluatorLog = new QMemberLifePatternLog("roommateScoreEvaluatorLog");
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(roommateScore)
                        .where(
                                roommateScore.myRoommate.id.eq(myRoommateId),
                                JPAExpressions.selectOne()
                                        .from(evaluatorLog)
                                        .where(
                                                evaluatorLog.memberLifePatternLogDegree.eq(roommateScore.memberLifePatternLogDegree),
                                                evaluatorLog.member.id.eq(memberId)
                                        )
                                        .exists()
                        )
                        .fetchOne()
        );
    }
}
