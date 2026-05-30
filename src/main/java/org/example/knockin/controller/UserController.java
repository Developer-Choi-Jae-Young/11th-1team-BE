package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/me")
public class UserController {
    @DeleteMapping("/")
    public CommonResponse<?> deleteUser(@AuthenticationPrincipal User user) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/profile/basic")
    public CommonResponse<?> saveBasicInfo() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/profile/lifestyle")
    public CommonResponse<?> saveLifeStyle() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/profile/roominfo")
    public CommonResponse<?> saveRoomInfo() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/profile/all")
    public CommonResponse<?> saveAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/profile/basic")
    public CommonResponse<?> modifyBasicInfo() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/profile/lifestyle")
    public CommonResponse<?> modifyLifeStyle() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/profile/roominfo")
    public CommonResponse<?> modifyRoomInfo() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/profile/all")
    public CommonResponse<?> modifyAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/preferences/lifestyle")
    public CommonResponse<?> savePreLifeStyle() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/preferences/conditions")
    public CommonResponse<?> savePreConditions() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/preferences/all")
    public CommonResponse<?> savePreAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/preferences/lifestyle")
    public CommonResponse<?> modifyPreLifeStyle() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/preferences/conditions")
    public CommonResponse<?> modifyPreConditions() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/preferences/all")
    public CommonResponse<?> modifyPreAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/preferences/all")
    public CommonResponse<?> findPreAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/profile/all")
    public CommonResponse<?> findProfileAll() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/visibility")
    public CommonResponse<?> changeProfileStatus() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/verifications")
    public CommonResponse<?> findVerificationList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/notification-settings")
    public CommonResponse<?> findAlaramSettingList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/notification-settings")
    public CommonResponse<?> modifyAlaramSetting() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}


