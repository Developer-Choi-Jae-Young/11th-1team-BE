package org.example.knockin.repository.room;

import java.util.List;
import org.example.knockin.repository.room.row.MatchingSeekerProfileRow;
import org.example.knockin.repository.room.row.MatchingSeekerRegionRow;
import org.example.knockin.repository.room.row.MatchingSeekerRoomTypeRow;

public interface RoomSeekerProfileRepositoryCustom {
    List<MatchingSeekerProfileRow> findAllSeekerProfileByMemberIdIn(List<Long> memberIds);

    List<MatchingSeekerRegionRow> findAllSeekerRegionByMemberIdIn(List<Long> memberIds);

    List<MatchingSeekerRoomTypeRow> findAllSeekerRoomTypeByMemberIdIn(List<Long> memberIds);
}
