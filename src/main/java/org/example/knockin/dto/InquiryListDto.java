package org.example.knockin.dto;

import lombok.Data;

import java.util.List;

@Data
public class InquiryListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<InquiryItem> inquiries;

        @Data
        public static class InquiryItem {
            private String id;
            private String title;
            private String writer;
            private String status;
            private String createAt;
            private String type;
        }
    }
}
