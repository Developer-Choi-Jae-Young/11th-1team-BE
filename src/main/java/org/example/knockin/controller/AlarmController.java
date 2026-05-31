package org.example.knockin.controller;

import org.example.knockin.dto.AlarmListDto;
import org.example.knockin.dto.AlarmSettingDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarms")
public class AlarmController {
    @GetMapping("")
    public CommonResponse<AlarmListDto.Response> findAlarmList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new AlarmListDto.Response());
    }

    @PatchMapping("/{id}/read")
    public CommonResponse<AlarmSettingDto.Response> modifyAlarmRead(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new AlarmSettingDto.Response());
    }

    @PatchMapping("/read-all")
    public CommonResponse<AlarmSettingDto.Response> modifyAllAlarmRead() {
        return CommonResponse.status(HttpStatus.OK).body(new AlarmSettingDto.Response());
    }
}
