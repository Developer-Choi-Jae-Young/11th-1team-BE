package org.example.knockin.controller;

import org.example.knockin.dto.ChatRequestDetailDto;
import org.example.knockin.dto.ChatRequestDto;
import org.example.knockin.dto.ChatRequestListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-requests")
public class ChatRequestController {
    @GetMapping("")
    public CommonResponse<ChatRequestListDto.Response> findChatRequestList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestListDto.Response());
    }

    @GetMapping("/{requestId}")
    public CommonResponse<ChatRequestDetailDto.Response> findChatRequest(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDetailDto.Response());
    }

    @PostMapping("")
    public CommonResponse<ChatRequestDto.Response> saveChatRequest(@RequestBody ChatRequestDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }


    @PostMapping("/{requestId}/accept")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }

    @PostMapping("/{requestId}/reject")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }

    @PostMapping("/{requestId}/cancel")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }
}
