package org.example.knockin.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoNoticeListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<NoticeItem> notices;

        @Data
        public static class NoticeItem {
            private String id;
            private String title;
            private String writer;
            private String createAt;
            private String type;
        }
    }
}
