package org.example.knockin.repository.room.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.MetaRoomTypesDto;
import org.example.knockin.entity.room.RoomType;
import org.example.knockin.repository.room.RoomTypeRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.example.knockin.entity.room.QRoomType.roomType;
import static org.example.knockin.entity.room.QRoomTypeFile.roomTypeFile;
import static org.example.knockin.entity.file.QFile.file;

@Repository
@RequiredArgsConstructor
public class RoomTypeRepositoryImpl implements RoomTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoomType> findByRoomTypes(List<Long> roomTypes) {
        return jpaQueryFactory.selectFrom(roomType).where(roomType.id.in(roomTypes)).fetch();
    }

    @Override
    public List<MetaRoomTypesDto.Response.RoomTypeItem> findAllByIsDeleted(Boolean isDeleted) {
        return jpaQueryFactory.select(Projections.fields(MetaRoomTypesDto.Response.RoomTypeItem.class,
                    roomType.id,
                    roomType.name,
                    file.savedFileName.as("image")
                )).from(roomType)
                .leftJoin(roomTypeFile).on(roomTypeFile.roomType.eq(roomType))
                .leftJoin(file).on(roomTypeFile.file.eq(file))
                .where(roomType.isDeleted.eq(isDeleted)).fetch();
    }
}