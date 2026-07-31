package org.example.knockin.repository.life.Impl;

import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;
import static org.example.knockin.entity.life.QMemberLifePattern.memberLifePattern;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BoardDetailDto;
import org.example.knockin.entity.file.QFile;
import org.example.knockin.entity.life.QLifePatternFile;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.life.MemberLifePatternRepositoryCustom;
import org.example.knockin.repository.life.row.MatchingLifestyleRow;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberLifePatternRepositoryImpl implements MemberLifePatternRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BoardDetailDto.Response.Lifestyle> getLifeStyleDto(Long memberId) {
        QLifePatternFile selectedPatternFile = new QLifePatternFile("selectedEditLifePatternFile");
        QLifePatternFile latestPatternFile = new QLifePatternFile("latestEditLifePatternFile");
        QFile patternImageFile = new QFile("editLifePatternImageFile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        BoardDetailDto.Response.Lifestyle.class,
                        memberLifePattern.id,
                        lifePattern.name,
                        lifePatternInformation.dvalue,
                        lifePatternInformation.description,
                        lifePattern.dtype,
                        patternImageFile.savedFileName
                ))
                .from(memberLifePattern)
                .join(memberLifePattern.lifePatternInformation, lifePatternInformation)
                .join(lifePatternInformation.lifePattern, lifePattern)
                .leftJoin(selectedPatternFile)
                .on(selectedPatternFile.id.eq(
                        JPAExpressions
                                .select(latestPatternFile.id.max())
                                .from(latestPatternFile)
                                .where(latestPatternFile.lifePattern.eq(lifePattern))
                ))
                .leftJoin(selectedPatternFile.file, patternImageFile)
                .on(patternImageFile.isDeleted.isFalse())
                .where(memberLifePattern.member.id.eq(memberId))
                .orderBy(lifePattern.sort.asc())
                .fetch();
    }

    @Override
    public boolean isExsitLifeStyle(Member member) {
        Long result = jpaQueryFactory.select(memberLifePattern.id).from(memberLifePattern).where(memberLifePattern.member.eq(member)).fetchFirst();
        return result != null;
    }

    @Override
    public List<MatchingLifestyleRow> findAllLifestyleByMemberIdIn(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        QLifePatternFile selectedPatternFile = new QLifePatternFile("selectedMatchingLifePatternFile");
        QLifePatternFile latestPatternFile = new QLifePatternFile("latestMatchingLifePatternFile");
        QFile patternImageFile = new QFile("matchingLifePatternImageFile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingLifestyleRow.class,
                        memberLifePattern.member.id,
                        memberLifePattern.id,
                        lifePattern.id,
                        lifePatternInformation.id,
                        lifePattern.name,
                        lifePatternInformation.dvalue,
                        lifePatternInformation.description,
                        lifePattern.dtype,
                        patternImageFile.savedFileName
                ))
                .from(memberLifePattern)
                .join(memberLifePattern.lifePatternInformation, lifePatternInformation)
                .join(lifePatternInformation.lifePattern, lifePattern)
                .leftJoin(selectedPatternFile)
                .on(selectedPatternFile.id.eq(
                        JPAExpressions
                                .select(latestPatternFile.id.max())
                                .from(latestPatternFile)
                                .where(latestPatternFile.lifePattern.eq(lifePattern))
                ))
                .leftJoin(selectedPatternFile.file, patternImageFile)
                .on(patternImageFile.isDeleted.isFalse())
                .where(memberLifePattern.member.id.in(memberIds))
                .orderBy(lifePattern.sort.asc(), memberLifePattern.id.asc())
                .fetch();
    }
}
