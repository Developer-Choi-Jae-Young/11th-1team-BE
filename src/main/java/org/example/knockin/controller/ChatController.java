package org.example.knockin.controller;

import lombok.RequiredArgsConstructor;
import org.example.knockin.global.api.CommonResponse;
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
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/")
    public CommonResponse<?> findChatList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @MessageMapping("/chats/{chatId}/messages")
    public void sendMessage(@DestinationVariable("chatId") Long chatId, @Payload Object request) {
        messagingTemplate.convertAndSend("/sub/chats/" + chatId, request);
    }

    @MessageMapping("/chats/{chatId}/leave")
    public void leaveChat(@DestinationVariable("chatId") Long chatId, @Payload Object request) {
        messagingTemplate.convertAndSend("/sub/chats/" + chatId, request);
    }
}
