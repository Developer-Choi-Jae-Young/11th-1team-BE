package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    @Data
    public static class Request {
        private String contents;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
