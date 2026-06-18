package org.example.knockin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.entity.alarm.AlarmType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@Data
public class AlarmSendDto {

    @Data
    public static class Request {
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String contents;
        private boolean isRead;
        private LocalDateTime expiredAt;
        private LocalDateTime createdAt;
        private Event event;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Event {
            private Long eventId;
            private AlarmType alarmType;
        }
    }
}
