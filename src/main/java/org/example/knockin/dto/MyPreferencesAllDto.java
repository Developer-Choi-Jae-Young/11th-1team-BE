package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class MyPreferencesAllDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Lifestyle> lifestyles;
        private List<Condition> conditions;

        @Data
        public static class Lifestyle {
            private String lifestyleId;
            private String name;
            private String value;
            private String description;
            private String type;
        }

        @Data
        public static class Condition {
            private String conditionsId;
            private String name;
        }
    }
}
