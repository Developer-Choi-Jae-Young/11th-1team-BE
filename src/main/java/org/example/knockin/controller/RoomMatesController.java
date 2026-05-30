package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roommates")
public class RoomMatesController {
    @GetMapping("/me")
    public CommonResponse<?> findMyRoomMate() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/me/{roommateId}")
    public CommonResponse<?> deleteMyRoomMate(@PathVariable Long roommateId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/me/calendar")
    public CommonResponse<?> findMyRoomMateCalendarList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/me/calendar/{id}")
    public CommonResponse<?> findMyRoomMateCalendar(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/me/calendar/types")
    public CommonResponse<?> findCalendarType() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/me/calendar")
    public CommonResponse<?> saveMyRoomMateCalendar() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/me/calendar/{id}")
    public CommonResponse<?> modifyMyRoomMateCalendar(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/me/calendar/{id}")
    public CommonResponse<?> deleteMyRoomMateCalendar(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
