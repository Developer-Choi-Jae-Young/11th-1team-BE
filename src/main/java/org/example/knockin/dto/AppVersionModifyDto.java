package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.entity.utils.PlatformType;
import org.example.knockin.entity.utils.UpdateType;

import java.time.LocalDateTime;

@Data
public class AppVersionModifyDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @Schema(description = "고유 번호")
        private Long id;
        @Schema(description = "앱 버전")
        private String version;
        @Schema(description = "플랫폼")
        private PlatformType platformType;
        @Schema(description = "업데이트 유형")
        private UpdateType updateType;
        @Schema(description = "최소 지원")
        private String minVersion;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        @Schema(description = "수정 일시")
        private LocalDateTime updatedAt;
    }
}
