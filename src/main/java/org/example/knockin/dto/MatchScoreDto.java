package org.example.knockin.dto;

import lombok.Data;

@Data
public class MatchScoreDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private Compatibility compatibility;

        @Data
        public static class Compatibility {
            private String score;
        }
    }
}
