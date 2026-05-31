package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class MyProfileAllDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Lifestyle> lifestyles;
        private String type;
        private String minDeposit;
        private String maxDeposit;
        private String minMounthRent;
        private String maxMounthRent;
        private String comeEnableAt;
        private List<Region> region;
        private List<RoomProfile> roomProfile;
        private String deposit;
        private String mounthRent;

        @Data
        public static class Lifestyle {
            private String lifestyleId;
            private String name;
            private String value;
            private String description;
            private String type;
        }

        @Data
        public static class Region {
            private String regionId;
            private String region;
        }

        @Data
        public static class RoomProfile {
            private String roomProfileId;
            private String roomProfileName;
        }
    }
}
