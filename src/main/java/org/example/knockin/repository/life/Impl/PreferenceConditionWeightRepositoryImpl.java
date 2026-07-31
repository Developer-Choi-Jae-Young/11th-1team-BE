package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QPreferenceConditionWeight.preferenceConditionWeight;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BoardDetailDto.Response.ConditionWeight;
import org.example.knockin.entity.file.QFile;
import org.example.knockin.entity.life.QLifePatternFile;
import org.example.knockin.repository.life.PreferenceConditionWeightRepositoryCustom;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionWeightRow;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PreferenceConditionWeightRepositoryImpl implements PreferenceConditionWeightRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ConditionWeight> getConditionWeightDtoByMemberId(Long memberId) {
        QLifePatternFile selectedPatternFile = new QLifePatternFile("selectedEditConditionWeightPatternFile");
        QLifePatternFile latestPatternFile = new QLifePatternFile("latestEditConditionWeightPatternFile");
        QFile patternImageFile = new QFile("editConditionWeightPatternImageFile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        ConditionWeight.class,
                        preferenceConditionWeight.id,
                        lifePattern.name,
                        patternImageFile.savedFileName
                ))
                .from(preferenceConditionWeight)
                .join(preferenceConditionWeight.lifePattern, lifePattern)
                .leftJoin(selectedPatternFile)
                .on(selectedPatternFile.id.eq(
                        JPAExpressions
                                .select(latestPatternFile.id.max())
                                .from(latestPatternFile)
                                .where(latestPatternFile.lifePattern.eq(lifePattern))
                ))
                .leftJoin(selectedPatternFile.file, patternImageFile)
                .on(patternImageFile.isDeleted.isFalse())
                .where(preferenceConditionWeight.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<MatchingPreferenceConditionWeightRow> findAllPreferenceConditionWeightByMemberIdIn(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        QLifePatternFile selectedPatternFile = new QLifePatternFile("selectedMatchingConditionWeightPatternFile");
        QLifePatternFile latestPatternFile = new QLifePatternFile("latestMatchingConditionWeightPatternFile");
        QFile patternImageFile = new QFile("matchingConditionWeightPatternImageFile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingPreferenceConditionWeightRow.class,
                        preferenceConditionWeight.member.id,
                        preferenceConditionWeight.id,
                        lifePattern.id,
                        lifePattern.name,
                        patternImageFile.savedFileName
                ))
                .from(preferenceConditionWeight)
                .join(preferenceConditionWeight.lifePattern, lifePattern)
                .leftJoin(selectedPatternFile)
                .on(selectedPatternFile.id.eq(
                        JPAExpressions
                                .select(latestPatternFile.id.max())
                                .from(latestPatternFile)
                                .where(latestPatternFile.lifePattern.eq(lifePattern))
                ))
                .leftJoin(selectedPatternFile.file, patternImageFile)
                .on(patternImageFile.isDeleted.isFalse())
                .where(preferenceConditionWeight.member.id.in(memberIds))
                .orderBy(lifePattern.sort.asc(), preferenceConditionWeight.id.asc())
                .fetch();
    }
}
