package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoTermsDto {
    @Data
    public static class Request {
        private String title;
        private String contents;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
