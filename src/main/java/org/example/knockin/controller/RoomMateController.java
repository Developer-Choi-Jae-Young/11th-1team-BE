package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roommate")
public class RoomMateController {
    @GetMapping("/boards")
    public CommonResponse<?> findBoardList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/boards/{boardId}")
    public CommonResponse<?> findBoard(@PathVariable Long boardId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/boards/likes")
    public CommonResponse<?> likeBoard() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/boards")
    public CommonResponse<?> saveBoard() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/boards/{boardId}")
    public CommonResponse<?> modifyBoard(@PathVariable Long boardId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/boards/{boardId}/reports")
    public CommonResponse<?> reportBoard(@PathVariable Long boardId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/boards/{boardId}")
    public CommonResponse<?> deleteBoard(@PathVariable Long boardId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/matches")
    public CommonResponse<?> findMatchList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/matches/{userId}")
    public CommonResponse<?> findMatch(@PathVariable Long userId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/matches/score")
    public CommonResponse<?> findMatchScore() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
