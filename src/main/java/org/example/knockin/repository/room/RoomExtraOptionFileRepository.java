package org.example.knockin.repository.room;

import org.example.knockin.entity.room.RoomExtraOption;
import org.example.knockin.entity.room.RoomExtraOptionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomExtraOptionFileRepository extends JpaRepository<RoomExtraOptionFile, Long> {
    Optional<RoomExtraOptionFile> findByRoomExtraOption(RoomExtraOption roomExtraOption);
}