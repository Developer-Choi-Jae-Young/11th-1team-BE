package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class MetaLifestylePatternsDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<PatternItem> patterns;

        @Data
        public static class PatternItem {
            private String id;
            private String name;
            private List<DetailItem> details;

            @Data
            public static class DetailItem {
                private String values;
                private String description;
            }
        }
    }
}
