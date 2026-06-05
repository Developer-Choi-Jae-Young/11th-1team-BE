package org.example.knockin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaveProfileLifeStyleDto {
    @Data
    @Builder
    public static class Request {
        private List<Long> lifestyles;
    }

    @Data
    @Builder
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
