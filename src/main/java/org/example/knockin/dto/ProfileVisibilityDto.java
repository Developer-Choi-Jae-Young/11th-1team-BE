package org.example.knockin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.entity.member.MemberState;

import java.time.LocalDateTime;

@Data
public class ProfileVisibilityDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private MemberState status;
    }

    @Data
    @Builder
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
