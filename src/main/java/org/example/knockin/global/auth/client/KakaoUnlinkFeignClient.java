package org.example.knockin.global.auth.client;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "kakao-unlink", url = "https://kapi.kakao.com")
public interface KakaoUnlinkFeignClient {
    @CircuitBreaker(name = "kakaoUnlink", fallbackMethod = "unlinkFallback")
    @PostMapping(value = "/v1/user/unlink", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String unlink(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, ?> form);

    default String unlinkFallback(String authorization, Map<String, ?> form, Exception e) {
        var log = LoggerFactory.getLogger(KakaoUnlinkFeignClient.class);
        if (e instanceof CallNotPermittedException) {
            log.warn("카카오 연결 끊기(Unlink) 생략. 서킷이 열려 있습니다.");
        } else if (e instanceof FeignException feignException) {
            log.warn("카카오 연결 끊기(Unlink) 실패. HTTP status: {}", feignException.status());
        } else {
            log.warn("카카오 연결 끊기(Unlink) 실패. 오류 유형: {}", e.getClass().getSimpleName());
        }
        return null;
    }
}
