package org.example.knockin.repository.room.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
}