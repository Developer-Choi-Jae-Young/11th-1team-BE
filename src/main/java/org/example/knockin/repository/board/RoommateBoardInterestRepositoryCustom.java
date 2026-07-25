package org.example.knockin.repository.board;

import java.util.Collection;
import java.util.List;

public interface RoommateBoardInterestRepositoryCustom {

    List<Long> findActiveBoardIdsByMemberIdAndBoardIds(Long memberId, Collection<Long> boardIds);
}
