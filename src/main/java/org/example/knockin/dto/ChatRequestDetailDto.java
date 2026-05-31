package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChatRequestDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private RequesterInfo requester;
        private RequesteeInfo requestee;

        @Data
        public static class RequesterInfo {
            private String name;
            private List<Lifestyle> lifeStyles;
            private String score;
            private String createAt;
        }

        @Data
        public static class RequesteeInfo {
            private String name;
            private List<Lifestyle> lifeStyles;
            private String score;
            private String createAt;
            private String isAgree;
        }

        @Data
        public static class Lifestyle {
            private String lifestyleId;
            private String name;
            private String value;
            private String description;
            private String type;
        }
    }
}
