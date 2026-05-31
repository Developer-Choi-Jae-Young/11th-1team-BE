package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoRoomTypeDto {
    @Data
    public static class Request {
        private String name;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
