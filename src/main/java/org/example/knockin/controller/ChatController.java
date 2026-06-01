package org.example.knockin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.ChatMessageDto;
import org.example.knockin.dto.ChatRoomListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Tag(name = "6. 채팅 (Chat)")
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    @GetMapping("")
    @Operation(summary = "채팅방 목록 조회")
    public CommonResponse<ChatRoomListDto.Response> findChatRoomList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRoomListDto.Response());
    }


    @MessageMapping("/chats/{chatId}/messages")
    @Operation(summary = "메시지 전송")
    public void sendMessage(@DestinationVariable("chatId") Long chatId, @Payload ChatMessageDto.Request request) {
        messagingTemplate.convertAndSend("/sub/chats/" + chatId, request);
    }

    @MessageMapping("/chats/{chatId}/leave")
    @Operation(summary = "채팅방 나가기")
    public void leaveChat(@DestinationVariable("chatId") Long chatId, @Payload ChatMessageDto.Request request) {
        messagingTemplate.convertAndSend("/sub/chats/" + chatId, request);
    }
}

