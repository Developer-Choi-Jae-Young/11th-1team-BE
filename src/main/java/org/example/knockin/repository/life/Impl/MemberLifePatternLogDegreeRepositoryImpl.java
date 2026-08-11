package org.example.knockin.repository.life.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.life.MemberLifePatternLogDegreeRepositoryCustom;
import org.springframework.stereotype.Repository;

import static org.example.knockin.entity.life.QMemberLifePatternLogDegree.memberLifePatternLogDegree;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;

@Repository
@RequiredArgsConstructor
public class MemberLifePatternLogDegreeRepositoryImpl implements MemberLifePatternLogDegreeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxmemberLifePatternLogDegree(Member member) {
        return jpaQueryFactory.select(memberLifePatternLogDegree.degree.max())
                .from(memberLifePatternLogDegree)
                .join(memberLifePatternLog).on(memberLifePatternLog.memberLifePatternLogDegree.eq(memberLifePatternLogDegree))
                .where(memberLifePatternLog.member.eq(member)).fetchOne();
    }
}
