package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.example.knockin.dto.ChatRoomDetailDto.RoommateMatchingRequiredInfo;

@Data
public class RoommateRequestDto {
    @Data
    public static class Request {
        @Schema(description = "채팅 방 id")
        private Long chatRoomId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        @Schema(description = "신규 룸메이트 매칭 요청 정보")
        private RoommateMatchingRequiredInfo roommateMatchingRequiredInfo;
    }
}