package org.example.knockin.repository.room;

import org.example.knockin.entity.room.RoomType;
import org.example.knockin.entity.room.RoomTypeFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeFileRepository extends JpaRepository<RoomTypeFile, Long> {
    Optional<RoomTypeFile> findByRoomType(RoomType roomType);
}