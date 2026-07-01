package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QPreferenceConditionWeightLog.preferenceConditionWeightLog;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.PreferenceConditionWeightLog;
import org.example.knockin.entity.life.QLifePattern;
import org.example.knockin.entity.life.QPreferenceConditionWeightLog;
import org.example.knockin.repository.life.PreferenceConditionWeightLogRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PreferenceConditionWeightLogRepositoryImpl implements PreferenceConditionWeightLogRepositoryCustom {

    public final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PreferenceConditionWeightLog> findLatestLogsWithFetchByMemberId(Long memberId) {
        QPreferenceConditionWeightLog subLog = new QPreferenceConditionWeightLog("subLog");
        QLifePattern subLifePattern = new QLifePattern("subLifePattern");

        return jpaQueryFactory
                .selectFrom(preferenceConditionWeightLog)
                .join(preferenceConditionWeightLog.lifePattern, lifePattern).fetchJoin()
                .where(
                        preferenceConditionWeightLog.id.in(
                                JPAExpressions
                                        .select(subLog.id.max())
                                        .from(subLog)
                                        .join(subLog.lifePattern, subLifePattern)
                                        .where(
                                                subLog.member.id.eq(memberId),
                                                subLifePattern.isDeleted.isFalse()
                                        )
                                        .groupBy(subLifePattern.id)
                        )
                )
                .orderBy(lifePattern.sort.asc())
                .fetch();
    }

}
