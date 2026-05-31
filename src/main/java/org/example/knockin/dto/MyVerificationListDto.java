package org.example.knockin.dto;

import lombok.Data;

@Data
public class MyVerificationListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private AuthInfo studentAuth;
        private AuthInfo employeeAuth;

        @Data
        public static class AuthInfo {
            private String isAccepted;
            private String email;
            private String createAt;
        }
    }
}
