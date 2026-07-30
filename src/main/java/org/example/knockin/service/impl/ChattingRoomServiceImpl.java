package org.example.knockin.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingRoom;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.ChattingErrorCode;
import org.example.knockin.repository.chat.ChattingRoomRepository;
import org.example.knockin.repository.chat.row.ChatRoomListRow;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChattingRoomServiceImpl {

    private final ChattingRoomRepository chattingRoomRepository;

    public List<ChatRoomListRow> findListRowsByMemberId(Long memberId) {
        return chattingRoomRepository.findListRowsByMemberId(memberId);
    }

    public ChattingRoom findByIdOrThrow(Long chatRoomId) {
        return chattingRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BusinessException(ChattingErrorCode.ROOM_NOT_FOUND));
    }

    public boolean existsActiveRoomBetweenMembers(Long requesterId, Long requesteeId) {
        return chattingRoomRepository.existsActiveRoomBetweenMembers(requesterId, requesteeId);
    }

    public long countActiveRoomsByMemberId(Long memberId) {
        return chattingRoomRepository.countActiveRoomsByMemberId(memberId);
    }

    public ChattingRoom save(ChattingRequired chattingRequired) {
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .chattingRequired(chattingRequired)
                .build();
        return chattingRoomRepository.save(chattingRoom);
    }
}
