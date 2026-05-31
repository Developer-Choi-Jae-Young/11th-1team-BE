package org.example.knockin.dto;

import lombok.Data;

@Data
public class TermsDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private String id;
        private String contents;
    }
}
