package org.example.knockin.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.ChatRoomListDto;
import org.example.knockin.repository.chat.ChattingRoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl {

    private final ChattingRoomRepository chattingRoomRepository;

    public List<ChatRoomListDto.Response> getChatRoomList(Long memberId) {
        return chattingRoomRepository.findByMemberId(memberId);
    }
}
