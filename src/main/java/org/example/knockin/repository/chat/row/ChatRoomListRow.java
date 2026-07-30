package org.example.knockin.repository.chat.row;

import java.time.LocalDateTime;
import org.example.knockin.entity.room.RoommateRequiredStatus;

public record ChatRoomListRow(
        Long chatRoomId,
        Long opponentMemberId,
        String memberName,
        String memberProfileImageUrl,
        LocalDateTime createdAt,
        RoommateRequiredStatus roommateStatus,
        Boolean isRoommate,
        String lastMessage,
        LocalDateTime lastMessageAt
) {
}
