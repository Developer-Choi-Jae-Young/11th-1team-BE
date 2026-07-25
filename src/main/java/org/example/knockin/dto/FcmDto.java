package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.entity.member.DevicePlatform;

@Data
public class FcmDto {

    @Data
    public static class Request {
        @Schema(description = "앱 설치 시 생성 후 SecureStore에 보관한 UUID")
        private String deviceId;

        @Schema(description = "Firebase Messaging이 생성한 실제 토큰")
        private String fcmToken;

        @Schema(description = "ANDROID / IOS")
        private DevicePlatform devicePlatform;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        @Schema
        private LocalDateTime updatedAt;
    }
}
