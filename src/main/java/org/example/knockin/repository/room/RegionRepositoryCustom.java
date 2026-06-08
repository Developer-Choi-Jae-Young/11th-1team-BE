package org.example.knockin.repository.room;

import org.example.knockin.entity.room.Region;

import java.util.List;
import org.jspecify.annotations.NullMarked;

public interface RegionRepositoryCustom {
    List<Region> findByRegions(List<Long> regions);
    List<Region> findByIdInWithChild(List<Long> regionIds);
}