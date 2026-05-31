package org.example.knockin.dto;

import lombok.Data;
import java.util.List;

@Data
public class BlockListDto {
    @Data
    public static class Request {
    }

    @Data
    public static class Response {
        private List<Block> blocks;

        @Data
        public static class Block {
            private String userId;
            private String name;
            private String createAt;
        }
    }
}
