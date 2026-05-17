package org.example.knockin.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.knockin.global.KnockInProps;
import org.example.knockin.global.auth.util.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

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
            Map<String, Object> result = new HashMap<>();
            result.put("accessToken", accessToken);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        } else {
            String redirectUrl = UriComponentsBuilder.fromUriString(knockInProps.getClientSuccessUrl())
                    .queryParam("accessToken", accessToken)
                    .build().toUriString();
            response.sendRedirect(redirectUrl);
        }
    }
}
