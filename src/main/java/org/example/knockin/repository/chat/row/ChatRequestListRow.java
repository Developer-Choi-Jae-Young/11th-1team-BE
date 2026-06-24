package org.example.knockin.repository.chat.row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.knockin.entity.chat.ChattingRequiredStatus;
import org.example.knockin.entity.member.Gender;

public record ChatRequestListRow(
        Long requiredId,
        ChattingRequiredStatus status,
        Long memberId,
        String memberName,
        LocalDate birth,
        Gender gender,
        LocalDateTime createdAt
) {
}
