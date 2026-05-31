package org.example.knockin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeleteUserDto {
    public static class Response {
        private LocalDateTime updatedAt;
    }
}
