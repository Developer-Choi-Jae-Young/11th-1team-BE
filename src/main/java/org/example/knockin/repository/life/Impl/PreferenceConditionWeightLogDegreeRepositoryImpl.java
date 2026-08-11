package org.example.knockin.repository.life.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.life.PreferenceConditionWeightLogDegreeRepositoryCustom;
import org.springframework.stereotype.Repository;

import static org.example.knockin.entity.life.QPreferenceConditionWeightLogDegree.preferenceConditionWeightLogDegree;
import static org.example.knockin.entity.life.QPreferenceConditionWeightLog.preferenceConditionWeightLog;

@Repository
@RequiredArgsConstructor
public class PreferenceConditionWeightLogDegreeRepositoryImpl implements PreferenceConditionWeightLogDegreeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxPreferenceConditionWeightLogDegree(Member member) {
        return jpaQueryFactory.select(preferenceConditionWeightLogDegree.degree.max())
                .from(preferenceConditionWeightLogDegree)
                .join(preferenceConditionWeightLog).on(preferenceConditionWeightLog.preferenceConditionWeightLogDegree.eq(preferenceConditionWeightLogDegree))
                .where(preferenceConditionWeightLog.member.eq(member)).fetchOne();
    }
}
