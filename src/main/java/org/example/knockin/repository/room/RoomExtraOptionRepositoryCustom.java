package org.example.knockin.repository.room;

import org.example.knockin.dto.MetaRoomAddOptionsDto;

import java.util.List;

public interface RoomExtraOptionRepositoryCustom {
    List<MetaRoomAddOptionsDto.Response.RoomAddOptionItem> findAllByIsDeleted(Boolean isDeleted);
}
