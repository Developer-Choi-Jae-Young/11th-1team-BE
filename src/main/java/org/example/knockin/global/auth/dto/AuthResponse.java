package org.example.knockin.global.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @Valid
    @NotNull(message = "인증 토큰이 누락되었습니다.")
    private String accessToken;

    private boolean basicInfo;
    private boolean preferenceInfo;
}

