package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;
import static org.example.knockin.entity.life.QMemberLifePatternLogDegree.memberLifePatternLogDegree;
import static org.example.knockin.entity.life.QPreferenceConditionLog.preferenceConditionLog;
import static org.example.knockin.entity.life.QPreferenceConditionLogDegree.preferenceConditionLogDegree;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.*;
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
        QPreferenceConditionLogDegree subDegree = new QPreferenceConditionLogDegree("subDegree");

        return jpaQueryFactory
                .selectFrom(preferenceConditionLog)
                .join(preferenceConditionLog.preferenceConditionLogDegree, preferenceConditionLogDegree).fetchJoin()
                .join(preferenceConditionLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .where(
                        preferenceConditionLog.member.id.eq(memberId),
                        lifePatternInformation.id.in(lifePatternInformationIds),
                        lifePattern.isDeleted.isFalse(),
                        preferenceConditionLogDegree.degree.eq(
                                JPAExpressions
                                        .select(subDegree.degree.max())
                                        .from(subLog)
                                        .join(subLog.preferenceConditionLogDegree, subDegree)
                                        .where(subLog.member.id.eq(memberId))
                        )
                )
                .orderBy(lifePattern.sort.asc(), preferenceConditionLog.id.asc())
                .fetch();
    }

    @Override
    public List<PreferenceConditionLog> findLatestLogsWithFetchByMemberId(Long memberId) {
        QPreferenceConditionLog subLog = new QPreferenceConditionLog("subLog");
        QPreferenceConditionLogDegree subDegree = new QPreferenceConditionLogDegree("subDegree");

        return jpaQueryFactory
                .selectFrom(preferenceConditionLog)
                .join(preferenceConditionLog.preferenceConditionLogDegree, preferenceConditionLogDegree).fetchJoin()
                .join(preferenceConditionLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .where(
                        preferenceConditionLog.member.id.eq(memberId),
                        preferenceConditionLogDegree.degree.eq(
                                JPAExpressions
                                        .select(subDegree.degree.max())
                                        .from(subLog)
                                        .join(subLog.preferenceConditionLogDegree, subDegree)
                                        .where(subLog.member.id.eq(memberId))
                        ),
                        lifePattern.isDeleted.isFalse()
                )
                .orderBy(lifePattern.sort.asc())
                .fetch();
    }
}
