package org.example.knockin.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.knockin.global.api.CommonResponse;
import org.example.knockin.global.auth.dto.AuthResponse;
import org.example.knockin.global.auth.util.TokenConstants;
import org.example.knockin.global.auth.util.TokenProvider;
import org.example.knockin.global.KnockInProps;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@NullMarked
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KnockInProps knockInProps;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String accessToken = tokenProvider.generateAccessToken(authentication);

        if (request.getAttribute("isSdkLogin") != null) {
            response.setContentType("application/json;charset=UTF-8");
            AuthResponse authResponse = AuthResponse.builder()
                    .accessToken(accessToken)
                    .basicInfo(true)
                    .preferenceInfo(false)
                    .build();
            
            CommonResponse<AuthResponse> commonResponse = CommonResponse.status(HttpStatus.OK).body(authResponse);
            response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
        } else {
            ResponseCookie accessTokenCookie = ResponseCookie.from(TokenConstants.ACCESS_TOKEN_COOKIE_NAME, accessToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(TokenProvider.ACCESS_TOKEN_EXPIRE_DURATION)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.sendRedirect(knockInProps.getClientSuccessUrl());
        }
    }
}
