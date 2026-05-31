package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlockDto {
    @Data
    public static class Request {
        private String userId;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
