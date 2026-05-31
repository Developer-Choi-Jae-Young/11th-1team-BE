package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoInquiryDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private InquiryDetail inquirie;

        @Data
        public static class InquiryDetail {
            private String id;
            private String title;
            private String contents;
            private String writer;
            private String status;
            private String createAt;
            private String type;
            private List<Reply> reply;

            @Data
            public static class Reply {
                private String id;
                private String title;
                private String contents;
                private String writer;
                private String createAt;
            }
        }
    }
}
