package org.example.knockin.repository.room.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.MetaRoomAddOptionsDto;
import org.example.knockin.repository.room.RoomExtraOptionRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.example.knockin.entity.room.QRoomExtraOption.roomExtraOption;
import static org.example.knockin.entity.room.QRoomExtraOptionFile.roomExtraOptionFile;
import static org.example.knockin.entity.file.QFile.file;

@Repository
@RequiredArgsConstructor
public class RoomExtraOptionRepositoryImpl implements RoomExtraOptionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MetaRoomAddOptionsDto.Response.RoomAddOptionItem> findAllByIsDeleted(Boolean isDeleted) {
        return jpaQueryFactory.select(Projections.fields(MetaRoomAddOptionsDto.Response.RoomAddOptionItem.class,
                roomExtraOption.id,
                roomExtraOption.name,
                file.savedFileName.as("image")
                )).from(roomExtraOption)
                .leftJoin(roomExtraOptionFile).on(roomExtraOptionFile.roomExtraOption.eq(roomExtraOption))
                .leftJoin(file).on(roomExtraOptionFile.file.eq(file))
                .where(roomExtraOption.isDeleted.eq(isDeleted)).fetch();
    }
}
