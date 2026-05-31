package org.example.knockin.controller;

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
public class AuthenticationController {
    @PostMapping("/student/send")
    public CommonResponse<EmailSendDto.Response> sendAuthNumStudent(@RequestBody EmailSendDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailSendDto.Response());
    }

    @PostMapping("/company/send")
    public CommonResponse<EmailSendDto.Response> sendAuthNumCompany(@RequestBody EmailSendDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailSendDto.Response());
    }

    @PostMapping("/student/confirm")
    public CommonResponse<EmailConfirmDto.Response> confirmAuthNumStudent(@RequestBody EmailConfirmDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailConfirmDto.Response());
    }

    @PostMapping("/company/confirm")
    public CommonResponse<EmailConfirmDto.Response> confirmAuthNumCompany(@RequestBody EmailConfirmDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new EmailConfirmDto.Response());
    }
}
