package org.example.knockin.repository.board;

import java.util.Collection;
import java.util.List;
import org.example.knockin.repository.board.row.BoardInterestCountRow;

public interface RoommateBoardInterestRepositoryCustom {

    List<Long> findActiveBoardIdsByMemberIdAndBoardIds(Long memberId, Collection<Long> boardIds);

    List<BoardInterestCountRow> findActiveInterestCountsByBoardIds(List<Long> boardIds);
}
