package org.example.knockin.repository.room;

import java.util.List;
import org.example.knockin.repository.room.row.MatchingOfferProfileRow;

public interface RoomOfferProfileRepositoryCustom {
    List<MatchingOfferProfileRow> findAllOfferProfileByMemberIdIn(List<Long> memberIds);
}
