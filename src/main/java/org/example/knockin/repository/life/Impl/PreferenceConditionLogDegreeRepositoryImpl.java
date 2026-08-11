package org.example.knockin.repository.life.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.life.PreferenceConditionLogDegreeRepositoryCustom;
import org.springframework.stereotype.Repository;

import static org.example.knockin.entity.life.QPreferenceConditionLogDegree.preferenceConditionLogDegree;
import static org.example.knockin.entity.life.QPreferenceConditionLog.preferenceConditionLog;

@Repository
@RequiredArgsConstructor
public class PreferenceConditionLogDegreeRepositoryImpl implements PreferenceConditionLogDegreeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxPreferenceConditionLogDegree(Member member) {
        return jpaQueryFactory.select(preferenceConditionLogDegree.degree.max())
                .from(preferenceConditionLogDegree)
                .join(preferenceConditionLog).on(preferenceConditionLog.preferenceConditionLogDegree.eq(preferenceConditionLogDegree))
                .where(preferenceConditionLog.member.eq(member)).fetchOne();
    }
}
