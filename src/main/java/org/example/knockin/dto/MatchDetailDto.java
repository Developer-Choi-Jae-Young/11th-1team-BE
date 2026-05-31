package org.example.knockin.dto;

import lombok.Data;

@Data
public class MatchDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private String id;
        private String score;
    }
}
