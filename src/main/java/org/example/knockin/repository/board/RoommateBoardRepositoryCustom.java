package org.example.knockin.repository.board;

import org.springframework.data.domain.Page;

public interface RoommateBoardRepositoryCustom {
    Page<RoommateBoardListRow> search(RoommateBoardSearchCondition condition);
}
