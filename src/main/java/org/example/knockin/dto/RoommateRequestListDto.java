package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoommateRequestListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<RoommateRequest> roommateRequests;

        @Data
        public static class RoommateRequest {
            private String requester;
            private String reqeustee;
            private String createAt;
            private String chatRoomId;
            private String isAgree;
        }
    }
}
