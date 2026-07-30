package org.example.knockin.service.impl;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.board.RoommateBoard;
import org.example.knockin.entity.board.RoommateBoardInterest;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.board.RoommateBoardInterestRepository;
import org.example.knockin.repository.board.row.BoardInterestCountRow;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoommateBoardInterestServiceImpl {
    private final RoommateBoardInterestRepository roommateBoardInterestRepository;

    public boolean existsActiveByBoardIdAndMemberId(Long boardId, Long memberId) {
        return roommateBoardInterestRepository.existsByRoommateBoardIdAndMemberIdAndIsDeletedIsFalse(boardId, memberId);
    }

    public List<Long> findActiveBoardIdsByMemberIdAndBoardIds(Long memberId, Collection<Long> boardIds) {
        if (boardIds.isEmpty()) {
            return List.of();
        }
        return roommateBoardInterestRepository.findActiveBoardIdsByMemberIdAndBoardIds(memberId, boardIds);
    }

    public void toggle(Member member, RoommateBoard roommateBoard) {
        roommateBoardInterestRepository.findByRoommateBoardAndMember(roommateBoard, member)
                .ifPresentOrElse(
                        RoommateBoardInterest::likeToggle,
                        () -> save(member, roommateBoard)
                );
    }

    private void save(Member member, RoommateBoard roommateBoard) {
        RoommateBoardInterest roommateBoardInterest = RoommateBoardInterest.builder()
                .member(member)
                .roommateBoard(roommateBoard)
                .isDeleted(false)
                .build();
        roommateBoardInterestRepository.save(roommateBoardInterest);
    }

    public List<BoardInterestCountRow> findActiveInterestCountsByBoardIds(List<Long> boardIds) {
        return roommateBoardInterestRepository.findActiveInterestCountsByBoardIds(boardIds);
    }
}
