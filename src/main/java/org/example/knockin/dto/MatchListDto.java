package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.knockin.entity.life.LifePatternType;
import org.example.knockin.entity.room.RoomProfileType;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        @Schema(description = "매칭 목록")
        private List<Match> matches;

        @Data
        public static class Match {
            @Schema(description = "고유 식별 ID")
            private Long userId;
            @Schema(description = "이름")
            private String name;
            @Schema(description = "좋아요 여부")
            private Boolean isLike;
            @Schema(description = "방 프로필 타입")
            private RoomProfileType roomProfileType;
            @Schema(description = "보증금")
            private Integer deposit;
            @Schema(description = "월세")
            private Integer mounthRent;
            @Schema(description = "최소 보증금")
            private Integer minDeposit;
            @Schema(description = "최소 월세")
            private Integer minMounthRent;
            @Schema(description = "최대 보증금")
            private Integer maxDeposit;
            @Schema(description = "최대 월세")
            private Integer maxMounthRent;
            @Schema(description = "입주 가능일")
            private LocalDateTime comeableAt;
            @Schema(description = "방 타입 목록")
            private List<Long> roomType;
            @Schema(description = "지역 ID")
            private Long region;
            @Schema(description = "점수")
            private Integer score;
            @Schema(description = "라이프스타일 목록")
            private List<Lifestyle> lifeStyles;
            @Schema(description = "조건 목록")
            private List<Condition> conditions;
        }

        @Data
        public static class Lifestyle {
            @Schema(description = "고유 식별 ID")
            private Long lifestyleId;
            @Schema(description = "이름")
            private String name;
            @Schema(description = "값")
            private String value;
            @Schema(description = "설명")
            private String description;
            @Schema(description = "타입/유형")
            private LifePatternType type;
        }

        @Data
        public static class Condition {
            @Schema(description = "고유 식별 ID")
            private Long conditionsId;
            @Schema(description = "이름")
            private String name;
        }
    }
}
