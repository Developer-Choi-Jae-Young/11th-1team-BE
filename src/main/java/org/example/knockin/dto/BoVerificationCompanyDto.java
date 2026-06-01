package org.example.knockin.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoVerificationCompanyDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
