package org.example.knockin.repository.room;

import org.example.knockin.entity.room.RoomType;

import java.util.List;

public interface RoomTypeRepositoryCustom {
    List<RoomType> findByRoomTypes(List<Long> roomTypes);
}