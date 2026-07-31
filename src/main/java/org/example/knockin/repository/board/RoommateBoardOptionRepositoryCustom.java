package org.example.knockin.repository.board;

import java.util.List;
import org.example.knockin.dto.BoardDetailDto.Response.RoomExtraOptionInfo;
import org.example.knockin.entity.board.RoommateBoardOption;

public interface RoommateBoardOptionRepositoryCustom {
    List<RoomExtraOptionInfo> getExtraOptionsByBoardId(Long boardId);

    List<RoommateBoardOption> findWithRoomExtraOptionByBoardId(Long boardId);
}
