package org.example.knockin.repository.room.row;

import org.example.knockin.global.util.HasMemberId;

public record MatchingSeekerRegionRow(
        Long memberId,
        String regionName,
        String parentRegionName,
        String grandParentRegionName
) implements HasMemberId {
}
