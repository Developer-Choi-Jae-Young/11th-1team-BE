package org.example.knockin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.knockin.entity.life.LifePatternType;
import java.time.LocalDateTime;
import java.util.List;
import org.example.knockin.entity.member.Gender;

@Data
public class BoardDetailDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        @Schema(description = "고유 식별 ID")
        private Long boardId;

        @Schema(description = "썸네일 URL 목록")
        private String thumbNailImageUrl;

        @Schema(description = "이미지 URL 목록")
        private List<String> imageUrls;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "보증금")
        private Integer deposit;

        @Schema(description = "관리비")
        private Integer managementCost;

        @Schema(description = "월세")
        private Integer monthlyRent;

        @Schema(description = "방 타입명")
        private String roomTypeName;

        @Schema(description = "지역명 full name")
        private String regionFullName;

        @Schema(description = "날짜 및 시간")
        private LocalDateTime createdAt;

        @Schema(description = "조회수")
        private Long hits;

        @Schema(description = "내용")
        private String contents;

        @Schema(description = "방 옵션 목록")
        private List<Long> roomOptions;

        @Schema(description = "라이프스타일 목록")
        private List<Lifestyle> lifeStyles;

        @Schema(description = "선호도 목록")
        private List<Preference> preferences;

        @Schema(description = "선호 룸메이트 조건 조건 목록")
        private List<Condition> conditions;

        @Schema(description = "등록자 이름")
        private String memberName;

        @Schema(description = "등록자 프로필 사진 URL")
        private String memberProfileImageUrl;

        @Schema(description = "등록자 나이")
        private String memberAge;

        @Schema(description = "등록자 성별")
        private Gender gender;

        @Schema(description = "학생 인증 여부")
        private Boolean isAuthStudent;

        @Schema(description = "직장인 인증 여부")
        private Boolean isAuthEmployee;

        @Schema(description = "적합도")
        private Compatibility compatibility;

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
        public static class Preference {
            @Schema(description = "고유 식별 ID")
            private Long preferencesId;
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

        @Data
        public static class Compatibility {
            @Schema(description = "점수")
            private Integer score;
            @Schema(description = "라이프스타일 정보 목록")
            private List<LifeStyleInfo> lifeStyleInfo;

            @Data
            public static class LifeStyleInfo {
                @Schema(description = "제목")
                private String title;
                @Schema(description = "백분율")
                private String percent;
            }
        }
    }
}
