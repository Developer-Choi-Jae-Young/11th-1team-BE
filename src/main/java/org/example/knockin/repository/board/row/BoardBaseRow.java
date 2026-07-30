package org.example.knockin.repository.board.row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.knockin.entity.member.Gender;

public record BoardBaseRow(
        Long boardId,
        String title,
        Integer deposit,
        Integer monthlyRent,
        Integer managementCost,
        LocalDateTime comeableDate,
        Long hits,
        String roomTypeName,
        String regionName,
        String parentRegionName,
        String grandParentRegionName,
        Long memberId,
        String memberName,
        String memberProfileImageUrl,
        LocalDate memberBirth,
        Gender memberGender,
        LocalDateTime createdAt
) {
}
