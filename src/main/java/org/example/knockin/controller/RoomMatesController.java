package org.example.knockin.controller;

import org.example.knockin.dto.CalendarDto;
import org.example.knockin.dto.CalendarTypesDto;
import org.example.knockin.dto.MyRoommateCalendarDetailDto;
import org.example.knockin.dto.MyRoommateCalendarListDto;
import org.example.knockin.dto.MyRoommateDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roommates")
public class RoomMatesController {
    @GetMapping("/me")
    public CommonResponse<MyRoommateDto.Response> findMyRoomMate() {
        return CommonResponse.status(HttpStatus.OK).body(new MyRoommateDto.Response());
    }

    @DeleteMapping("/me/{roommateId}")
    public CommonResponse<MyRoommateDto.Response> deleteMyRoomMate(@PathVariable Long roommateId) {
        return CommonResponse.status(HttpStatus.OK).body(new MyRoommateDto.Response());
    }

    @GetMapping("/me/calendar")
    public CommonResponse<MyRoommateCalendarListDto.Response> findMyRoomMateCalendarList(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        return CommonResponse.status(HttpStatus.OK).body(new MyRoommateCalendarListDto.Response());
    }

    @GetMapping("/me/calendar/{id}")
    public CommonResponse<MyRoommateCalendarDetailDto.Response> findMyRoomMateCalendar(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new MyRoommateCalendarDetailDto.Response());
    }

    @GetMapping("/me/calendar/types")
    public CommonResponse<CalendarTypesDto.Response> findCalendarType() {
        return CommonResponse.status(HttpStatus.OK).body(new CalendarTypesDto.Response());
    }

    @PostMapping("/me/calendar")
    public CommonResponse<CalendarDto.Response> saveMyRoomMateCalendar(@RequestBody CalendarDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new CalendarDto.Response());
    }

    @PutMapping("/me/calendar/{id}")
    public CommonResponse<CalendarDto.Response> modifyMyRoomMateCalendar(@PathVariable Long id, @RequestBody CalendarDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new CalendarDto.Response());
    }

    @DeleteMapping("/me/calendar/{id}")
    public CommonResponse<CalendarDto.Response> deleteMyRoomMateCalendar(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new CalendarDto.Response());
    }
}
