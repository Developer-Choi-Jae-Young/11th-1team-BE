package org.example.knockin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.ChatMessageDto;
import org.example.knockin.dto.ChatRoomDto;
import org.example.knockin.dto.ChatRoomDto.Response;
import org.example.knockin.dto.ChatRoomListDto;
import org.example.knockin.dto.ChatRoomLeftEvent;
import org.example.knockin.entity.chat.ChatRoomMember;
import org.example.knockin.global.exception.BusinessException;
import org.example.knockin.global.exception.ChattingErrorCode;
import org.example.knockin.repository.chat.ChatRoomMemberRepository;
import org.example.knockin.repository.chat.ChattingRoomRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ApplicationEventPublisher publisher;
    private final ChatRoomAccessService chatRoomAccessService;

    public List<ChatRoomListDto.Response> getChatRoomList(Long memberId) {
        return chattingRoomRepository.findByMemberId(memberId);
    }

    @Transactional
    public ChatRoomDto.Response leaveChatRoom(Long memberId, Long chatRoomId) {
        ChatRoomMember roomMember = chatRoomMemberRepository.findActiveMemberByRoomIdAndMemberId(chatRoomId, memberId)
                .orElseThrow(() -> new BusinessException(ChattingErrorCode.ROOM_MEMBER_NOT_FOUND));
        roomMember.left();

        LocalDateTime now = LocalDateTime.now();
        publisher.publishEvent(new ChatRoomLeftEvent(memberId, chatRoomId, now));
        return new Response(now);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChatRoomLeft(ChatRoomLeftEvent event) {
        ChatMessageDto.Response response = ChatMessageDto.Response.userLeft(event.chatRoomId(), event.memberId(), event.leftAt());
        messagingTemplate.convertAndSend("/sub/chats/" + event.chatRoomId(), response);
    }

    public void sendMessage(Long chatId, ChatMessageDto.Request request, Long senderId) {
        validateMessageRequest(request);
        chatRoomAccessService.checkCanSendMessage(chatId, senderId);
        ChatMessageDto.Response response = ChatMessageDto.Response.chatMessage(chatId, senderId, request, LocalDateTime.now());
        messagingTemplate.convertAndSend("/sub/chats/" + chatId, response);
    }

    private void validateMessageRequest(ChatMessageDto.Request request) {
        if (request == null
                || !StringUtils.hasText(request.getClientMessageId())
                || request.getType() == null) {
            throw new BusinessException(ChattingErrorCode.MESSAGE_PAYLOAD_INVALID);
        }

        switch (request.getType()) {
            case TEXT -> validateTextMessage(request);
            case IMAGE -> validateImageMessage(request);
        }
    }

    private void validateTextMessage(ChatMessageDto.Request request) {
        if (!StringUtils.hasText(request.getMessage())) {
            throw new BusinessException(ChattingErrorCode.MESSAGE_PAYLOAD_INVALID);
        }
    }

    private void validateImageMessage(ChatMessageDto.Request request) {
        if (!StringUtils.hasText(request.getImageUrl())) {
            throw new BusinessException(ChattingErrorCode.MESSAGE_PAYLOAD_INVALID);
        }
    }
}
