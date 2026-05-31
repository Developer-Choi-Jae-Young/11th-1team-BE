package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileVisibilityDto {
    @Data
    public static class Request {
        private String status;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
