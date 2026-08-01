package org.example.knockin.repository.chat.impl;

import static org.example.knockin.entity.chat.QChattingScore.chattingScore;
import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePatternLog.memberLifePatternLog;
import static org.example.knockin.entity.life.QPreferenceConditionWeightLog.preferenceConditionWeightLog;
import static org.example.knockin.entity.member.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.chat.ChattingScore;
import org.example.knockin.repository.chat.ChattingScoreRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChattingScoreRepositoryImpl implements ChattingScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChattingScore> findWithScoreDetailsByChattingRequiredIdAndMemberId(Long chattingRequiredId, Long memberId) {
        return jpaQueryFactory
                .selectFrom(chattingScore)
                .join(chattingScore.lifePatternInformationLog, memberLifePatternLog).fetchJoin()
                .join(memberLifePatternLog.member, member).fetchJoin()
                .join(memberLifePatternLog.lifePatternInformation, lifePatternInformation).fetchJoin()
                .join(lifePatternInformation.lifePattern, lifePattern).fetchJoin()
                .leftJoin(chattingScore.preferenceConditionWeightLog, preferenceConditionWeightLog).fetchJoin()
                .where(
                        chattingScore.chattingRequired.id.eq(chattingRequiredId),
                        member.id.eq(memberId)
                )
                .fetch();
    }
}