package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.MemberLifePatternLog;
import org.example.knockin.entity.life.QLifePattern;
import org.example.knockin.entity.life.QLifePatternInformation;
import org.example.knockin.entity.life.QMemberLifePatternLog;
import org.example.knockin.repository.life.MemberLifePatternLogRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberLifePatternLogRepositoryImpl implements MemberLifePatternLogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MemberLifePatternLog> findLatestLogsWithFetchByMemberId(Long memberId) {
        QMemberLifePatternLog subLog = new QMemberLifePatternLog("subLog");
        QLifePatternInformation subInfo = new QLifePatternInformation("subInfo");
        QLifePattern subLifePattern = new QLifePattern("subLifePattern");

        return jpaQueryFactory
                .selectFrom(memberLifePatternLog)
                .join(memberLifePatternLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .where(
                        memberLifePatternLog.id.in(
                                JPAExpressions
                                        .select(subLog.id.max())
                                        .from(subLog)
                                        .join(subLog.lifePatternInformation, subInfo)
                                        .join(subInfo.lifePattern, subLifePattern)
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
