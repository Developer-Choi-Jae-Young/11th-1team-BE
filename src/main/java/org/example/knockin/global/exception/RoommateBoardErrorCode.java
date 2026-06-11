package org.example.knockin.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.board.RoommateBoard;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoommateBoardErrorCode implements ErrorCode {
    ROOMMATE_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "룸메이트 게시글 조회에 실패했습니다."),
    ROOMMATE_BOARD_FILE_COUNT_EXCEEDED(HttpStatus.NOT_FOUND, "게시글당 최대 "+ RoommateBoard.IMAGE_MAXIMUM + "장까지 업로드할 수 있습니다."),
    ROOMMATE_BOARD_FILE_COUNT_THUMBNAIL_EXCEEDED(HttpStatus.NOT_FOUND, "썸네일은 최대 " + RoommateBoard.THUMBNAIL_MAXIMUM + "장 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
