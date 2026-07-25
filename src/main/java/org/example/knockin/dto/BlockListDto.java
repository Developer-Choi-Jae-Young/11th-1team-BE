package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlockListDto {
    @Data
    public static class Request {
    }

    @Data
    @Builder
    public static class Response {
        @Schema(description = "blocks")
        private List<Block> blocks;

        @Data
        @Builder
        public static class Block {
            @Schema(description = "차단한 사용자 ID")
            private Long userId;
            @Schema(description = "차단한 사용자 이름")
            private String name;
            @Schema(description = "생성 일시")
            private LocalDateTime createAt;
        }
    }
}