package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarms")
public class AlarmController {
    @GetMapping("/")
    public CommonResponse<?> findAlarmList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/{id}/read")
    public CommonResponse<?> saveAlarmRead(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/read-all")
    public CommonResponse<?> saveAlarmReadAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
