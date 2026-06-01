package org.example.knockin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.knockin.dto.EmailConfirmDto;
import org.example.knockin.dto.EmailSendDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/verify")
@Tag(name = "7. 신원인증/차단")
public class AuthenticationController {
    @PostMapping("/student/send")
    @Operation(summary = "학생 이메일 인증번호 발송")
    public CommonResponse<EmailSendDto.Response> sendAuthNumStudent(@RequestBody EmailSendDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailSendDto.Response());
    }

    @PostMapping("/company/send")
    @Operation(summary = "직장인 이메일 인증번호 발송")
    public CommonResponse<EmailSendDto.Response> sendAuthNumCompany(@RequestBody EmailSendDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailSendDto.Response());
    }

    @PostMapping("/student/confirm")
    @Operation(summary = "학생 이메일 인증번호 확인")
    public CommonResponse<EmailConfirmDto.Response> confirmAuthNumStudent(@RequestBody EmailConfirmDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailConfirmDto.Response());
    }

    @PostMapping("/company/confirm")
    @Operation(summary = "직장인 이메일 인증번호 확인")
    public CommonResponse<EmailConfirmDto.Response> confirmAuthNumCompany(@RequestBody EmailConfirmDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailConfirmDto.Response());
    }
}

