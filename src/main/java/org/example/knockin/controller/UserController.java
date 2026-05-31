package org.example.knockin.controller;

import org.example.knockin.dto.AlarmSettingDto;
import org.example.knockin.dto.DeleteUserDto;
import org.example.knockin.dto.ModifyPreferencesAllDto;
import org.example.knockin.dto.ModifyPreferencesConditionsDto;
import org.example.knockin.dto.ModifyPreferencesLifeStyleDto;
import org.example.knockin.dto.ModifyProfileAllDto;
import org.example.knockin.dto.ModifyProfileBasicDto;
import org.example.knockin.dto.ModifyProfileLifeStyleDto;
import org.example.knockin.dto.ModifyProfileRoomInfoDto;
import org.example.knockin.dto.MyBoardListDto;
import org.example.knockin.dto.MyNotificationSettingsDto;
import org.example.knockin.dto.MyPreferencesAllDto;
import org.example.knockin.dto.MyProfileAllDto;
import org.example.knockin.dto.MyVerificationListDto;
import org.example.knockin.dto.ProfileVisibilityDto;
import org.example.knockin.dto.SavePreferencesAllDto;
import org.example.knockin.dto.SavePreferencesConditionsDto;
import org.example.knockin.dto.SavePreferencesLifeStyleDto;
import org.example.knockin.dto.SaveProfileAllDto;
import org.example.knockin.dto.SaveProfileBasicDto;
import org.example.knockin.dto.SaveProfileLifeStyleDto;
import org.example.knockin.dto.SaveProfileRoomInfoDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
public class UserController {
    @DeleteMapping("/")
    public CommonResponse<DeleteUserDto.Response> deleteUser(@AuthenticationPrincipal User user) {
        return CommonResponse.status(HttpStatus.OK).body(new DeleteUserDto.Response());
    }

    @PostMapping("/profile/basic")
    public CommonResponse<SaveProfileBasicDto.Response> saveBasicInfo(@RequestBody SaveProfileBasicDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SaveProfileBasicDto.Response());
    }

    @PostMapping("/profile/lifestyle")
    public CommonResponse<SaveProfileLifeStyleDto.Response> saveLifeStyle(@RequestBody SaveProfileLifeStyleDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SaveProfileLifeStyleDto.Response());
    }

    @PostMapping("/profile/roominfo")
    public CommonResponse<SaveProfileRoomInfoDto.Response> saveRoomInfo(@RequestBody SaveProfileRoomInfoDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SaveProfileRoomInfoDto.Response());
    }

    @PostMapping("/profile/all")
    public CommonResponse<SaveProfileAllDto.Response> saveAll(@RequestBody SaveProfileAllDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SaveProfileAllDto.Response());
    }

    @PutMapping("/profile/basic")
    public CommonResponse<ModifyProfileBasicDto.Response> modifyBasicInfo(@RequestBody ModifyProfileBasicDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyProfileBasicDto.Response());
    }

    @PutMapping("/profile/lifestyle")
    public CommonResponse<ModifyProfileLifeStyleDto.Response> modifyLifeStyle(@RequestBody ModifyProfileLifeStyleDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyProfileLifeStyleDto.Response());
    }

    @PutMapping("/profile/roominfo")
    public CommonResponse<ModifyProfileRoomInfoDto.Response> modifyRoomInfo(@RequestBody ModifyProfileRoomInfoDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyProfileRoomInfoDto.Response());
    }

    @PutMapping("/profile/all")
    public CommonResponse<ModifyProfileAllDto.Response> modifyAll(@RequestBody ModifyProfileAllDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyProfileAllDto.Response());
    }

    @PostMapping("/preferences/lifestyle")
    public CommonResponse<SavePreferencesLifeStyleDto.Response> savePreLifeStyle(@RequestBody SavePreferencesLifeStyleDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SavePreferencesLifeStyleDto.Response());
    }

    @PostMapping("/preferences/conditions")
    public CommonResponse<SavePreferencesConditionsDto.Response> savePreConditions(@RequestBody SavePreferencesConditionsDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SavePreferencesConditionsDto.Response());
    }

    @PostMapping("/preferences/all")
    public CommonResponse<SavePreferencesAllDto.Response> savePreAll(@RequestBody SavePreferencesAllDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new SavePreferencesAllDto.Response());
    }

    @PutMapping("/preferences/lifestyle")
    public CommonResponse<ModifyPreferencesLifeStyleDto.Response> modifyPreLifeStyle(@RequestBody ModifyPreferencesLifeStyleDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyPreferencesLifeStyleDto.Response());
    }

    @PutMapping("/preferences/conditions")
    public CommonResponse<ModifyPreferencesConditionsDto.Response> modifyPreConditions(@RequestBody ModifyPreferencesConditionsDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyPreferencesConditionsDto.Response());
    }

    @PutMapping("/preferences/all")
    public CommonResponse<ModifyPreferencesAllDto.Response> modifyPreAll(@RequestBody ModifyPreferencesAllDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ModifyPreferencesAllDto.Response());
    }

    @GetMapping("/preferences/all")
    public CommonResponse<MyPreferencesAllDto.Response> findPreAll() {
        return CommonResponse.status(HttpStatus.OK).body(new MyPreferencesAllDto.Response());
    }

    @GetMapping("/profile/all")
    public CommonResponse<MyProfileAllDto.Response> findProfileAll() {
        return CommonResponse.status(HttpStatus.OK).body(new MyProfileAllDto.Response());
    }

    @PatchMapping("/visibility")
    public CommonResponse<ProfileVisibilityDto.Response> changeProfileStatus(@RequestBody ProfileVisibilityDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new ProfileVisibilityDto.Response());
    }

    @GetMapping("/boards")
    public CommonResponse<MyBoardListDto.Response> findMyBoardList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new MyBoardListDto.Response());
    }

    @GetMapping("/verifications")
    public CommonResponse<MyVerificationListDto.Response> findVerificationList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new MyVerificationListDto.Response());
    }

    @GetMapping("/notification-settings")
    public CommonResponse<MyNotificationSettingsDto.Response> findAlaramSettingList() {
        return CommonResponse.status(HttpStatus.OK).body(new MyNotificationSettingsDto.Response());
    }


    @PatchMapping("/notification-settings")
    public CommonResponse<AlarmSettingDto.Response> modifyAlaramSetting(@RequestBody AlarmSettingDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new AlarmSettingDto.Response());
    }
}
