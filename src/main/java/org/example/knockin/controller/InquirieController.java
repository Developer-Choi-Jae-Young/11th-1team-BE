package org.example.knockin.controller;

import org.example.knockin.dto.InquiryCategoryListDto;
import org.example.knockin.dto.InquiryDetailDto;
import org.example.knockin.dto.InquiryDto;
import org.example.knockin.dto.InquiryListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inquiries")
public class InquirieController {
    @GetMapping("")
    public CommonResponse<InquiryListDto.Response> findInquirieList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryListDto.Response());
    }

    @GetMapping("/{inquiryId}")
    public CommonResponse<InquiryDetailDto.Response> findInquirie(@PathVariable Long inquiryId) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryDetailDto.Response());
    }

    @GetMapping("/categorys")
    public CommonResponse<InquiryCategoryListDto.Response> findInquirieCategoryList() {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryCategoryListDto.Response());
    }

    @PostMapping("")
    public CommonResponse<InquiryDto.Response> saveInquiry(@RequestBody InquiryDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryDto.Response());
    }
}
