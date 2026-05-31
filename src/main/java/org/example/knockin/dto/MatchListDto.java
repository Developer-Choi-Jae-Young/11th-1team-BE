package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class MatchListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Match> matches;

        @Data
        public static class Match {
            private String userId;
            private String name;
            private String isLike;
            private String roomProfileType;
            private String deposit;
            private String mounthRent;
            private String minDeposit;
            private String minMounthRent;
            private String maxDeposit;
            private String maxMounthRent;
            private String comeableAt;
            private List<String> roomType;
            private String region;
            private String score;
            private List<Lifestyle> lifeStyles;
            private List<Condition> conditions;
        }

        @Data
        public static class Lifestyle {
            private String lifestyleId;
            private String name;
            private String value;
            private String description;
            private String type;
        }

        @Data
        public static class Condition {
            private String conditionsId;
            private String name;
        }
    }
}
