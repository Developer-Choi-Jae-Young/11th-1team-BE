package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class MyRoommateCalendarListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Calendar> calendars;

        @Data
        public static class Calendar {
            private String calendarId;
            private String writer;
            private String startDt;
            private String endDt;
            private String createAt;
            private String type;
            private String title;
        }
    }
}
