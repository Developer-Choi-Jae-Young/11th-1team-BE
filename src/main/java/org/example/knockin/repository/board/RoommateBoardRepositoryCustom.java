package org.example.knockin.repository.board;

import org.example.knockin.dto.BoardListDto;
import org.springframework.data.domain.Page;

public interface RoommateBoardRepositoryCustom {
    Page<BoardListDto.Response> search(RoommateBoardSearchCondition condition);
}
