package org.example.knockin.repository.room;

import org.example.knockin.entity.room.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long>, RoomTypeRepositoryCustom {
    Page<RoomType> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}