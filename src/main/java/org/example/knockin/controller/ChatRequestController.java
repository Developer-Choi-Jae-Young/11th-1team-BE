package org.example.knockin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "5. 채팅 요청")
public class ChatRequestController {
    @GetMapping("")
    @Operation(summary = "채팅 요청 목록 조회")
    public CommonResponse<ChatRequestListDto.Response> findChatRequestList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestListDto.Response());
    }

    @GetMapping("/{requestId}")
    @Operation(summary = "채팅 요청 상세 조회")
    public CommonResponse<ChatRequestDetailDto.Response> findChatRequest(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDetailDto.Response());
    }

    @PostMapping("")
    @Operation(summary = "채팅 요청 저장")
    public CommonResponse<ChatRequestDto.Response> saveChatRequest(@RequestBody ChatRequestDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }


    @PostMapping("/{requestId}/accept")
    @Operation(summary = "채팅 요청 수락")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }

    @PostMapping("/{requestId}/reject")
    @Operation(summary = "채팅 요청 거절")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }

    @PostMapping("/{requestId}/cancel")
    @Operation(summary = "채팅 요청 취소")
    public CommonResponse<ChatRequestDto.Response> saveChatRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new ChatRequestDto.Response());
    }
}

