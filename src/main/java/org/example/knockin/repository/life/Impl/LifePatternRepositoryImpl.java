package org.example.knockin.repository.life.Impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BoLifeStylePatternDetailDto;
import org.example.knockin.dto.BoLifeStylePatternListDto;
import org.example.knockin.dto.MetaLifestylePatternsDto;
import org.example.knockin.repository.life.LifePatternRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.example.knockin.entity.life.QLifePattern.lifePattern;
import static org.example.knockin.entity.life.QLifePatternInformation.lifePatternInformation;

@Repository
@RequiredArgsConstructor
public class LifePatternRepositoryImpl implements LifePatternRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MetaLifestylePatternsDto.Response.PatternItem> findLifeStylePatterns() {
        return jpaQueryFactory
                .from(lifePattern)
                .leftJoin(lifePatternInformation).on(lifePatternInformation.lifePattern.id.eq(lifePattern.id))
                .where(lifePattern.isDeleted.eq(false))
                .orderBy(lifePattern.sort.asc())
                .transform(groupBy(lifePattern.id).list(Projections.fields(MetaLifestylePatternsDto.Response.PatternItem.class,
                                        lifePattern.id,
                                        lifePattern.name,
                                        lifePattern.dtype.as("type"),
                                        list(Projections.fields(MetaLifestylePatternsDto.Response.PatternItem.DetailItem.class,
                                                lifePatternInformation.dvalue.as("values"),
                                                lifePatternInformation.description
                                        )).as("details"))));
    }

    @Override
    public List<BoLifeStylePatternListDto.Response.PatternItem> findLifeStylePatternList(Pageable pageable) {
        return jpaQueryFactory
                .from(lifePattern)
                .leftJoin(lifePatternInformation).on(lifePatternInformation.lifePattern.id.eq(lifePattern.id))
                .where(lifePattern.isDeleted.eq(false))
                .orderBy(lifePattern.sort.asc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .transform(groupBy(lifePattern.id).list(Projections.fields(BoLifeStylePatternListDto.Response.PatternItem.class,
                        lifePattern.id,
                        lifePattern.name,
                        lifePattern.dtype.as("type"),
                        list(Projections.fields(BoLifeStylePatternListDto.Response.PatternItem.DetailItem.class,
                                lifePatternInformation.dvalue.as("values"),
                                lifePatternInformation.description
                        )).as("details"))));
    }

    @Override
    public BoLifeStylePatternDetailDto.Response findLifeStylePattern(Long patternId) {
        return jpaQueryFactory
                .from(lifePattern)
                .leftJoin(lifePatternInformation)
                .on(lifePatternInformation.lifePattern.id.eq(lifePattern.id))
                .where(lifePattern.id.eq(patternId), lifePattern.isDeleted.eq(false))
                .transform(groupBy(lifePattern.id).as(Projections.fields(
                                        BoLifeStylePatternDetailDto.Response.class,
                                        lifePattern.id,
                                        lifePattern.name,
                                        lifePattern.dtype.as("type"),
                                        list(Projections.fields(BoLifeStylePatternDetailDto.Response.DetailItem.class,
                                                        lifePatternInformation.dvalue.as("values"),
                                                        lifePatternInformation.description)).as("details")))).get(patternId);
    }
}