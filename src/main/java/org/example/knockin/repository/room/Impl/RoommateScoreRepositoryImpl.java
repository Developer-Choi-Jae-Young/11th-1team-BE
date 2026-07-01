package org.example.knockin.repository.room.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;
import static org.example.knockin.entity.life.QPreferenceConditionWeightLog.preferenceConditionWeightLog;
import static org.example.knockin.entity.member.QMember.member;
import static org.example.knockin.entity.room.QRoommateScore.roommateScore;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.RoommateScore;
import org.example.knockin.repository.room.RoommateScoreRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoommateScoreRepositoryImpl implements RoommateScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoommateScore> findWithScoreDetailsByMyRoommateId(Long myRoommateId) {
        return jpaQueryFactory
                .selectFrom(roommateScore)
                .join(roommateScore.lifePatternInformationLog, memberLifePatternLog).fetchJoin()
                .join(memberLifePatternLog.member, member).fetchJoin()
                .join(memberLifePatternLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .leftJoin(roommateScore.preferenceConditionWeightLog, preferenceConditionWeightLog).fetchJoin()
                .where(roommateScore.myRoommate.id.eq(myRoommateId))
                .fetch();
    }
}
