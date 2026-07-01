package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QPreferenceConditionLog.preferenceConditionLog;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.PreferenceConditionLog;
import org.example.knockin.entity.life.QPreferenceConditionLog;
import org.example.knockin.repository.life.PreferenceConditionLogRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PreferenceConditionLogRepositoryImpl implements PreferenceConditionLogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PreferenceConditionLog> findLatestLogsWithFetchByMemberId(Long memberId, List<Long> lifePatternInformationIds) {
        if (memberId == null || lifePatternInformationIds == null || lifePatternInformationIds.isEmpty()) {
            return List.of();
        }

        QPreferenceConditionLog subLog = new QPreferenceConditionLog("subLog");

        return jpaQueryFactory
                .selectFrom(preferenceConditionLog)
                .join(preferenceConditionLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .where(
                        preferenceConditionLog.member.id.eq(memberId),
                        lifePatternInformation.id.in(lifePatternInformationIds),
                        lifePattern.isDeleted.isFalse(),
                        preferenceConditionLog.id.in(
                                JPAExpressions
                                        .select(subLog.id.max())
                                        .from(subLog)
                                        .where(
                                                subLog.member.id.eq(memberId),
                                                subLog.lifePatternInformation.id.in(lifePatternInformationIds)
                                        )
                                        .groupBy(subLog.lifePatternInformation.id)
                        )
                )
                .orderBy(lifePattern.sort.asc(), preferenceConditionLog.id.asc())
                .fetch();
    }
}
