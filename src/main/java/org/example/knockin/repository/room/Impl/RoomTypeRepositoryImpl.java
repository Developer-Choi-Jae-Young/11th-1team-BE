package org.example.knockin.repository.room.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.RoomType;
import org.example.knockin.repository.room.RoomTypeRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.example.knockin.entity.room.QRoomType.roomType;

@Repository
@RequiredArgsConstructor
public class RoomTypeRepositoryImpl implements RoomTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoomType> findByRoomTypes(List<Long> roomTypes) {
        return jpaQueryFactory.selectFrom(roomType).where(roomType.id.in(roomTypes)).fetch();
    }
}