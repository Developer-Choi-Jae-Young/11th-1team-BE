package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarDto {
    @Data
    public static class Request {
        private String roommateId;
        private String title;
        private String contents;
        private String startDt;
        private String endDt;
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
