package org.example.knockin.repository.room.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.QRegion;
import org.example.knockin.entity.room.Region;
import org.example.knockin.repository.room.RegionRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.example.knockin.entity.room.QRegion.region;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Region> findByRegions(List<Long> regions) {
        return jpaQueryFactory.selectFrom(region).where(region.id.in(regions)).fetch();
    }

    @Override
    public List<Region> findByIdInWithChild(List<Long> regionIds) {
        if (regionIds.isEmpty()) return List.of();
        List<Long> uniqueIds = regionIds.stream().distinct().toList();

        QRegion region = new QRegion("region");
        QRegion parent = new QRegion("parent");
        QRegion grandParent = new QRegion("grandParent");

        return jpaQueryFactory
                .selectFrom(region)
                .leftJoin(region.parent, parent)
                .leftJoin(parent.parent, grandParent)
                .where(region.id.in(uniqueIds).or(parent.id.in(uniqueIds)).or(grandParent.id.in(uniqueIds)))
                .fetch();
    }
}