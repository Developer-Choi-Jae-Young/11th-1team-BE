package org.example.knockin.repository.board.impl;

import static org.example.knockin.entity.board.QRoommateBoardInterest.roommateBoardInterest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.repository.board.RoommateBoardInterestRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoommateBoardInterestRepositoryImpl implements RoommateBoardInterestRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findActiveBoardIdsByMemberIdAndBoardIds(Long memberId, Collection<Long> boardIds) {
        if (boardIds.isEmpty()) {
            return List.of();
        }

        return jpaQueryFactory
                .select(roommateBoardInterest.roommateBoard.id)
                .from(roommateBoardInterest)
                .where(
                        roommateBoardInterest.member.id.eq(memberId),
                        roommateBoardInterest.roommateBoard.id.in(boardIds),
                        roommateBoardInterest.isDeleted.isFalse()
                )
                .fetch();
    }
}
