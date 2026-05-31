package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailConfirmDto {
    @Data
    public static class Request {
        private String email;
        private String authNo;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
