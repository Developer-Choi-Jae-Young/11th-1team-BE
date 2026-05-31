package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModifyProfileRoomInfoDto {
    @Data
    public static class Request {
        private RoomProfileType type;
        private Integer minDeposit;
        private Integer maxDeposit;
        private Integer minMounthRent;
        private Integer maxMounthRent;
        private LocalDate comeEnableAt;
        private List<Integer> region;
        private List<Integer> roomProfile;
        private Integer deposit;
        private Integer mounthRent;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
