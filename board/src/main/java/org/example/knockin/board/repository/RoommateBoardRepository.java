package org.example.knockin.board.repository;

import org.example.knockin.board.entity.RoommateBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoommateBoardRepository extends JpaRepository<RoommateBoard, Long>, RoommateBoardRepositoryCustom {
}