package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-requests")
public class ChatRequestController {
    @GetMapping("/")
    public CommonResponse<?> findChatRequestList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{requestId}")
    public CommonResponse<?> findChatRequest(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/")
    public CommonResponse<?> saveChatRequestList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/accept")
    public CommonResponse<?> saveChatRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/reject")
    public CommonResponse<?> saveChatRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/cancel")
    public CommonResponse<?> saveChatRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
