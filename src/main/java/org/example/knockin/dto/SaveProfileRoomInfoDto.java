package org.example.knockin.dto;

import lombok.Builder;
import lombok.Data;
import org.example.knockin.entity.room.RoomProfileType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaveProfileRoomInfoDto {
    @Data
    @Builder
    public static class Request {
        private RoomProfileType type;
        private Integer minDeposit;
        private Integer maxDeposit;
        private Integer minMounthRent;
        private Integer maxMounthRent;
        private LocalDateTime comeEnableAt;
        private List<Long> region;
        private List<Long> roomProfile;
        private Integer deposit;
        private Integer mounthRent;
        private boolean isComeableAtNegotiable;
    }

    @Data
    @Builder
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
