package org.example.knockin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.example.knockin.entity.auth.AuthenticationType;
import org.example.knockin.entity.board.RoommateBoardBadgeType;
import org.example.knockin.entity.member.Gender;

@Data
public class BoardListDto {
    @Data
    public static class Request {
        List<Long> regionIds;
        List<Long> roomTypeIds;
        Gender gender;
        Integer minDeposit;
        Integer maxDeposit;
        Integer minMounthRent;
        Integer maxMounthRent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        Long id;
        String imageUrl;
        String title;
        Integer deposit;
        Integer monthlyRent;
        Integer managementCost;
        List<String> roomTypes;
        LocalDateTime comeableDate;
        String regionFullName;
        String memberName;
        List<AuthenticationType> authentications;
        Long hits;
        List<RoommateBoardBadgeType> badges;
    }
}
