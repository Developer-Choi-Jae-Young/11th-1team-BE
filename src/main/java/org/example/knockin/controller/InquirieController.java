package org.example.knockin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "9. 알림/고객센터")
public class InquirieController {
    @GetMapping("")
    @Operation(summary = "문의 목록 조회")
    public CommonResponse<InquiryListDto.Response> findInquirieList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryListDto.Response());
    }

    @GetMapping("/{inquiryId}")
    @Operation(summary = "문의 상세 조회")
    public CommonResponse<InquiryDetailDto.Response> findInquirie(@PathVariable Long inquiryId) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryDetailDto.Response());
    }

    @GetMapping("/categorys")
    @Operation(summary = "문의 카테고리 목록 조회")
    public CommonResponse<InquiryCategoryListDto.Response> findInquirieCategoryList() {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryCategoryListDto.Response());
    }

    @PostMapping("")
    @Operation(summary = "문의 저장")
    public CommonResponse<InquiryDto.Response> saveInquiry(@RequestBody InquiryDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new InquiryDto.Response());
    }
}

