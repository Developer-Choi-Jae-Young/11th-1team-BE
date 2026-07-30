package org.example.knockin.repository.chat.row;

public record ChatRoomUnreadCountRow(
        Long chatRoomId,
        Long messageCount
) {
}
