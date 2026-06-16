package org.example.knockin.repository.room.row;

import org.example.knockin.global.util.HasMemberId;

public record MatchingSeekerRoomTypeRow(
        Long memberId,
        String roomTypeName
) implements HasMemberId {
}
