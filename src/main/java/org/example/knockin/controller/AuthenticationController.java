package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/verify")
public class AuthenticationController {
    @PostMapping("/student/send")
    public CommonResponse<?> sendAuthNumStudent() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/company/send")
    public CommonResponse<?> sendAuthNumCompany() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/student/confirm")
    public CommonResponse<?> confirmAuthNumStudent() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/company/confirm")
    public CommonResponse<?> confirmAuthNumCompany() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
