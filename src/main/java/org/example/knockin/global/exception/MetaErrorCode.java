package org.example.knockin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MetaErrorCode implements ErrorCode {
    REGION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "지역 조회에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
