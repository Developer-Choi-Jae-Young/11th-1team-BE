package org.example.knockin.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.knockin.global.auth.client.KakaoUnlinkFeignClient;
import org.example.knockin.verification.entity.LoginProviderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOauth2DeleteService extends AbstractOauth2DeleteService {
    @Value("${kakao.admin.key}")
    private String kakaoAdminKey;
    private final KakaoUnlinkFeignClient kakaoUnlinkFeignClient;

    @Override
    public boolean requestUnlink(String providerId) {
        String response = kakaoUnlinkFeignClient.unlink("KakaoAK " + kakaoAdminKey, Map.of("target_id_type", "user_id", "target_id", providerId));
        return StringUtils.hasText(response);
    }

    @Override
    public LoginProviderType getProviderType() {
        return LoginProviderType.KAKAO;
    }
}
