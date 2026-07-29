package org.example.knockin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomTypeErrorCode implements ErrorCode {
    ROOM_TYPE_NOT_FOUNT(15000, HttpStatus.INTERNAL_SERVER_ERROR, "방 형태를 조회하지 못하였습니다."),
    ROOM_EXTRA_OPTION_NOT_FOUNT(15001, HttpStatus.INTERNAL_SERVER_ERROR, "방 추가 옵션 정보를 조회하지 못하였습니다.");;

    private final Integer no;
    private final HttpStatus httpStatus;
    private final String message;
}
