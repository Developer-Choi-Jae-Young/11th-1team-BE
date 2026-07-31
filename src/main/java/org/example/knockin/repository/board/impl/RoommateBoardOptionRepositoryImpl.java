package org.example.knockin.repository.board.impl;

import static org.example.knockin.entity.board.QRoommateBoardOption.roommateBoardOption;
import static org.example.knockin.entity.room.QRoomExtraOption.roomExtraOption;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BoardDetailDto.Response.RoomExtraOptionInfo;
import org.example.knockin.entity.board.RoommateBoardOption;
import org.example.knockin.entity.file.QFile;
import org.example.knockin.entity.room.QRoomExtraOptionFile;
import org.example.knockin.repository.board.RoommateBoardOptionRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoommateBoardOptionRepositoryImpl implements RoommateBoardOptionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoomExtraOptionInfo> getExtraOptionsByBoardId(Long boardId) {
        QRoomExtraOptionFile selectedOptionFile = new QRoomExtraOptionFile("selectedBoardOptionFile");
        QRoomExtraOptionFile latestOptionFile = new QRoomExtraOptionFile("latestBoardOptionFile");
        QFile optionImageFile = new QFile("boardOptionImageFile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoomExtraOptionInfo.class,
                        roomExtraOption.id,
                        roomExtraOption.name,
                        optionImageFile.savedFileName
                ))
                .from(roommateBoardOption)
                .join(roommateBoardOption.roomExtraOption, roomExtraOption)
                .leftJoin(selectedOptionFile)
                .on(selectedOptionFile.id.eq(
                        JPAExpressions
                                .select(latestOptionFile.id.max())
                                .from(latestOptionFile)
                                .where(latestOptionFile.roomExtraOption.eq(roomExtraOption))
                ))
                .leftJoin(selectedOptionFile.file, optionImageFile)
                .on(optionImageFile.isDeleted.isFalse())
                .where(
                        roommateBoardOption.roommateBoard.id.eq(boardId),
                        roomExtraOption.isDeleted.isFalse()
                )
                .fetch();
    }

    @Override
    public List<RoommateBoardOption> findWithRoomExtraOptionByBoardId(Long boardId) {
        return jpaQueryFactory
                .select(roommateBoardOption)
                .distinct()
                .from(roommateBoardOption)
                .join(roommateBoardOption.roomExtraOption).fetchJoin()
                .where(roommateBoardOption.roommateBoard.id.eq(boardId), roomExtraOption.isDeleted.isFalse())
                .fetch();
    }
}
