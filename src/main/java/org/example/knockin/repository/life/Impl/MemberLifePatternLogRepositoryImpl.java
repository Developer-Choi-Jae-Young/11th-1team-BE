package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;
import static org.example.knockin.entity.life.QMemberLifePatternLogDegree.memberLifePatternLogDegree;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.MemberLifePatternLog;
import org.example.knockin.entity.life.QMemberLifePatternLog;
import org.example.knockin.entity.life.QMemberLifePatternLogDegree;
import org.example.knockin.repository.life.MemberLifePatternLogRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberLifePatternLogRepositoryImpl implements MemberLifePatternLogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MemberLifePatternLog> findLatestLogsWithFetchByMemberId(Long memberId) {
        QMemberLifePatternLog subLog = new QMemberLifePatternLog("subLog");
        QMemberLifePatternLogDegree subDegree = new QMemberLifePatternLogDegree("subDegree");

        return jpaQueryFactory
                .selectFrom(memberLifePatternLog)
                .join(memberLifePatternLog.memberLifePatternLogDegree, memberLifePatternLogDegree).fetchJoin()
                .join(memberLifePatternLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .where(
                        memberLifePatternLog.member.id.eq(memberId),
                        memberLifePatternLogDegree.degree.eq(
                                JPAExpressions
                                        .select(subDegree.degree.max())
                                        .from(subLog)
                                        .join(subLog.memberLifePatternLogDegree, subDegree)
                                        .where(subLog.member.id.eq(memberId))
                        ),
                        lifePattern.isDeleted.isFalse()
                )
                .orderBy(lifePattern.sort.asc())
                .fetch();
    }
}
