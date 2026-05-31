package org.example.knockin.controller;

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
public class RoomMateRequestController {
    @PostMapping("")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequest(@RequestBody RoommateRequestDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/accept")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/reject")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @PostMapping("/{requestId}/cancel")
    public CommonResponse<RoommateRequestDto.Response> saveRoomMateRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestDto.Response());
    }

    @GetMapping("")
    public CommonResponse<RoommateRequestListDto.Response> findRoomMateRequestList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new RoommateRequestListDto.Response());
    }
}
