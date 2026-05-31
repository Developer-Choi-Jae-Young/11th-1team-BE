package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class CalendarTypesDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Type> types;

        @Data
        public static class Type {
            private String id;
            private String name;
        }
    }
}
