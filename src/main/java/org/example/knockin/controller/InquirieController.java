package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inquiries")
public class InquirieController {
    @GetMapping("/")
    public CommonResponse<?> findInquirieList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{inquiryId}")
    public CommonResponse<?> findInquirie(@PathVariable Long inquiryId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/categorys")
    public CommonResponse<?> findInquirieCategorys() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/")
    public CommonResponse<?> saveInquirieList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
