package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailSendDto {
    @Data
    public static class Request {
        private String email;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
