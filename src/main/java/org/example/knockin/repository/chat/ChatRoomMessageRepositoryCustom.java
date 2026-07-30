package org.example.knockin.repository.chat;

import java.util.List;
import org.example.knockin.dto.ChatRoomDetailDto.ChatMessage;
import org.example.knockin.repository.chat.row.ChatRoomUnreadCountRow;

public interface ChatRoomMessageRepositoryCustom {
    List<ChatMessage> findChatMessageDto(Long chatRoomId);

    List<ChatRoomUnreadCountRow> findUnreadMessageCounts(Long memberId, List<Long> chatRoomIds);

    long markUnreadMessagesAsRead(Long chatRoomId, Long memberId);
}
