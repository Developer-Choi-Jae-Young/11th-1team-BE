package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogOutDto {
    @Data
    public static class Request {
        private String accessToken;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
