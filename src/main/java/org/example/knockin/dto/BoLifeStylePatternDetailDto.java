package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoLifeStylePatternDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
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
