package org.example.knockin.repository.board;

import java.time.LocalDateTime;
import java.util.List;
import org.example.knockin.entity.auth.AuthenticationType;
import org.example.knockin.entity.board.RoommateBoardBadgeType;

public record RoommateBoardListRow(
        Long id,
        String imageUrl,
        String title,
        Integer deposit,
        Integer monthlyRent,
        Integer managementCost,
        List<String> roomTypes,
        LocalDateTime comeableDate,
        String regionFullName,
        String memberName,
        List<AuthenticationType> authentications,
        Long hits,
        List<RoommateBoardBadgeType> badges
) {
}
