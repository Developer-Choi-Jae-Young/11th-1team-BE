package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roommate-requests")
public class RoomMateRequestController {
    @PostMapping("/")
    public CommonResponse<?> saveRoomMateRequest() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/accept")
    public CommonResponse<?> saveRoomMateRequestAccept(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/reject")
    public CommonResponse<?> saveRoomMateRequestReject(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{requestId}/cancel")
    public CommonResponse<?> saveRoomMateRequestCancel(@PathVariable Long requestId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/")
    public CommonResponse<?> findRoomMateRequestList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
