package org.example.knockin.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.knockin.member.entity.MemberRole;
import org.example.knockin.verification.entity.LoginProviderType;

@Data
public class MyAccountDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        @Schema(description = "회원 권한 (USER, ADMIN 등)")
        private MemberRole role;

        @Schema(description = "로그인 제공자 (KAKAO, APPLE)")
        private LoginProviderType providerType;
    }
}
