package org.example.knockin.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.member.Member;
import org.example.knockin.auth.util.OAuth2UserInfoProvider;
import org.example.knockin.dto.OAuth2UserInfo;
import org.example.knockin.dto.PrincipalDetails;
import org.example.knockin.service.impl.MemberServiceImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberServiceImpl memberService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> oAuth2UserAttributes;

        if ("apple".equalsIgnoreCase(registrationId)) {
            String idToken = (String) userRequest.getAdditionalParameters().get("id_token");
            if (idToken == null || idToken.isEmpty()) {
                idToken = userRequest.getAccessToken().getTokenValue();
            }

            oAuth2UserAttributes = decodeJwtPayload(idToken);
        } else {
            oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        }

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        if (userNameAttributeName == null || userNameAttributeName.isEmpty()) {
            userNameAttributeName = "sub";
        }

        Class<? extends OAuth2UserInfo> infoClass = OAuth2UserInfoProvider.findByRegistrationId(registrationId).getInfoClass();
        OAuth2UserInfo oAuth2UserInfo = objectMapper.convertValue(oAuth2UserAttributes, infoClass);
        Member member = memberService.getOrSave(oAuth2UserInfo);

        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }

    private Map<String, Object> decodeJwtPayload(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("올바르지 않은 JWT 토큰입니다.");
            }
            String payloadJson = new String(java.util.Base64.getUrlDecoder().decode(parts[1]), java.nio.charset.StandardCharsets.UTF_8);
            Map<String, Object> claims = objectMapper.readValue(payloadJson, Map.class);
            if (claims.containsKey("sub")) {
                Object subObj = claims.get("sub");
                try {
                    claims.put("id", Math.abs(subObj.hashCode()));
                } catch (Exception ignored) {
                }
            }
            return claims;
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Apple id_token 파싱 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
