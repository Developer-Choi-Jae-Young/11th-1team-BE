package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModifyPreferencesAllDto {
    @Data
    public static class Request {
        private List<Integer> lifestyles;
        private List<Integer> conditions;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
