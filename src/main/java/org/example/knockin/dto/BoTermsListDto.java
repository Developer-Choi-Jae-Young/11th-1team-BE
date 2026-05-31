package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoTermsListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<TermsItem> terms;

        @Data
        public static class TermsItem {
            private String id;
            private String title;
            private String createAt;
        }
    }
}
