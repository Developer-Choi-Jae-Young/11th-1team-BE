package org.example.knockin.repository.room;

import org.example.knockin.entity.room.Region;

import java.util.List;

public interface RegionRepositoryCustom {
    List<Region> findByRegions(List<Long> regions);
}