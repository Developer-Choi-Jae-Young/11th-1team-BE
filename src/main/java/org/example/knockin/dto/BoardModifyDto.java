package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.dto.BoardDto.Request.FileDto;
import org.example.knockin.entity.board.RoommateBoard;

public class BoardModifyDto {
    @Data
    public static class Request {

        @NotNull
        @Schema(description = "제목")
        private String title;

        @NotNull
        @Schema(description = "보증금")
        private int deposit;

        @NotNull
        @Schema(description = "월세")
        private int monthlyRent;

        @NotNull
        @Schema(description = "관리비")
        private int managementCost;

        @NotNull
        @Schema(description = "룸 형태 ID")
        private Long roomTypeId;

        @NotNull
        @Schema(description = "지역 ID")
        private Long regionId;

        @Schema(description = "입주 가능시기")
        private LocalDateTime comeableAt;

        @Schema(description = "삭제 추가 옵션 ID 목록")
        private List<Long> deleteExtraOptionId;

        @Schema(description = "신규 추가 옵션 ID 목록")
        private List<Long> newExtraOptionId;

        @NotNull
        @Schema(description = "내용")
        private String contents;

        @Schema(description = "유지할 이미지 DTO")
        private List<ExistingFileDto> existingImages;

        @Schema(description = "신규 이미지 목록")
        private List<FileDto> newImages;

        @Data
        public static class ExistingFileDto {
            @Schema(description = "게시물 파일 식별 ID")
            private Long boardFileId;

            @Schema(description = "이미지 URL")
            private boolean thumbnail;
        }

        @AssertTrue(message = "이미지는 최대 "+ RoommateBoard.IMAGE_MAXIMUM +"개까지 등록할 수 있습니다.")
        @Schema(hidden = true)
        public boolean validateImage() {
            int existingCount = existingImages == null ? 0 : existingImages.size();
            int newCount = newImages == null ? 0 : newImages.size();
            return existingCount + newCount <= RoommateBoard.IMAGE_MAXIMUM;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        @Schema(description = "날짜 및 시간")
        private LocalDateTime updatedAt;
    }
}
