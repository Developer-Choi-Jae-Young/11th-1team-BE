package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class AlarmListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Alarm> alarms;

        @Data
        public static class Alarm {
            private String title;
            private String contents;
            private String isRead;
            private String createAt;
        }
    }
}
