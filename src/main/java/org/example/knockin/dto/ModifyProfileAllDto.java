package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModifyProfileAllDto {
    @Data
    public static class Request {
        private String name;
        private LocalDate birth;
        private Gender gender;
        private String email;
        private List<Integer> terms;
        private List<Integer> lifestyles;
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
