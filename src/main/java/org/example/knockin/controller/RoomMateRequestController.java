package org.example.knockin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.knockin.dto.RoommateRequestDto;
import org.example.knockin.dto.RoommateRequestListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roommate-requests")
@Tag(name = "8. 룸메이트 관리")
public class RoomMateRequestController {
    @PostMapping("")
    @Operation(summary = "룸메이트 요청 저장")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequest(@RequestBody RoommateRequestDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/accept")
    @Operation(summary = "룸메이트 요청 수락")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/reject")
    @Operation(summary = "룸메이트 요청 거절")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/cancel")
    @Operation(summary = "룸메이트 요청 취소")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @GetMapping("")
    @Operation(summary = "룸메이트 요청 목록 조회")
    public CommonResponse<RoommateRequestListDto.Response> findRoomMateRequestList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestListDto.Response());
    }
}

