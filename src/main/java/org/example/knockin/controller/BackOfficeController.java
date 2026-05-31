package org.example.knockin.controller;

import org.example.knockin.dto.BoInquiryDetailDto;
import org.example.knockin.dto.BoInquiryListDto;
import org.example.knockin.dto.BoInquiryReplyDto;
import org.example.knockin.dto.BoLifeStylePatternDetailDto;
import org.example.knockin.dto.BoLifeStylePatternDto;
import org.example.knockin.dto.BoLifeStylePatternListDto;
import org.example.knockin.dto.BoNoticeDetailDto;
import org.example.knockin.dto.BoNoticeDto;
import org.example.knockin.dto.BoNoticeListDto;
import org.example.knockin.dto.BoRoomTypeDetailDto;
import org.example.knockin.dto.BoRoomTypeDto;
import org.example.knockin.dto.BoRoomTypeListDto;
import org.example.knockin.dto.BoTermsDetailDto;
import org.example.knockin.dto.BoTermsDto;
import org.example.knockin.dto.BoTermsListDto;
import org.example.knockin.dto.BoVerificationCompanyDto;
import org.example.knockin.dto.BoVerificationCompanyListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bo")
public class BackOfficeController {
    @PostMapping("/terms")
    public CommonResponse<BoTermsDto.Response> saveTerms(@RequestBody BoTermsDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsDto.Response());
    }

    @PutMapping("/terms/{termsId}/draft")
    public CommonResponse<BoTermsDto.Response> modifyTerms(@PathVariable Long termsId, @RequestBody BoTermsDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsDto.Response());
    }

    @GetMapping("/terms")
    public CommonResponse<BoTermsListDto.Response> findTermsList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsListDto.Response());
    }

    @GetMapping("/terms/{termsId}")
    public CommonResponse<BoTermsDetailDto.Response> findTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsDetailDto.Response());
    }

    @DeleteMapping("/terms/{termsId}")
    public CommonResponse<BoTermsDto.Response> deleteTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsDto.Response());
    }

    @PutMapping("/terms/{termsId}/publish")
    public CommonResponse<BoTermsDto.Response> modifyLastTerms(@PathVariable Long termsId, @RequestBody BoTermsDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoTermsDto.Response());
    }

    @PostMapping("/room-types")
    public CommonResponse<BoRoomTypeDto.Response> saveRoomType(@RequestBody BoRoomTypeDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoRoomTypeDto.Response());
    }

    @GetMapping("/room-types")
    public CommonResponse<BoRoomTypeListDto.Response> findRoomTypeList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoRoomTypeListDto.Response());
    }

    @PutMapping("/room-types/{id}")
    public CommonResponse<BoRoomTypeDto.Response> modifyRoomType(@PathVariable Long id, @RequestBody BoRoomTypeDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoRoomTypeDto.Response());
    }

    @DeleteMapping("/room-types/{id}")
    public CommonResponse<BoRoomTypeDto.Response> deleteRoomType(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoRoomTypeDto.Response());
    }

    @GetMapping("/room-types/{id}")
    public CommonResponse<BoRoomTypeDetailDto.Response> findRoomType(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoRoomTypeDetailDto.Response());
    }

    @GetMapping("/lifestyle-patterns")
    public CommonResponse<BoLifeStylePatternListDto.Response> findLifeStylePatternList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoLifeStylePatternListDto.Response());
    }

    @GetMapping("/lifestyle-patterns/{id}")
    public CommonResponse<BoLifeStylePatternDetailDto.Response> findLifeStylePattern(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoLifeStylePatternDetailDto.Response());
    }

    @PostMapping("/lifestyle-patterns")
    public CommonResponse<BoLifeStylePatternDto.Response> saveLifeStylePattern(@RequestBody BoLifeStylePatternDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoLifeStylePatternDto.Response());
    }

    @PutMapping("/lifestyle-patterns/{id}")
    public CommonResponse<BoLifeStylePatternDto.Response> modifyLifeStylePattern(@PathVariable Long id, @RequestBody BoLifeStylePatternDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoLifeStylePatternDto.Response());
    }

    @DeleteMapping("/lifestyle-patterns/{id}")
    public CommonResponse<BoLifeStylePatternDto.Response> deleteLifeStylePattern(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoLifeStylePatternDto.Response());
    }

    @GetMapping("/verifications/company")
    public CommonResponse<BoVerificationCompanyListDto.Response> findVerificationsCompanyList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoVerificationCompanyListDto.Response());
    }

    @PatchMapping("/verifications/company/{id}/approve")
    public CommonResponse<BoVerificationCompanyDto.Response> saveVerificationsCompany(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoVerificationCompanyDto.Response());
    }

    @PostMapping("/notices")
    public CommonResponse<BoNoticeDto.Response> saveNotice(@RequestBody BoNoticeDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoNoticeDto.Response());
    }

    @GetMapping("/notices")
    public CommonResponse<BoNoticeListDto.Response> findNoticeList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoNoticeListDto.Response());
    }

    @GetMapping("/notices/{id}")
    public CommonResponse<BoNoticeDetailDto.Response> findNotice(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoNoticeDetailDto.Response());
    }

    @PutMapping("/notices/{id}")
    public CommonResponse<BoNoticeDto.Response> modifyNotice(@PathVariable Long id, @RequestBody BoNoticeDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoNoticeDto.Response());
    }

    @DeleteMapping("/notices/{id}")
    public CommonResponse<BoNoticeDto.Response> deleteNotice(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoNoticeDto.Response());
    }

    @PostMapping("/inquiries")
    public CommonResponse<BoInquiryReplyDto.Response> saveInquiryReply(@RequestBody BoInquiryReplyDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BoInquiryReplyDto.Response());
    }

    @GetMapping("/inquiries")
    public CommonResponse<BoInquiryListDto.Response> findInquirieList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BoInquiryListDto.Response());
    }

    @GetMapping("/inquiries/{id}")
    public CommonResponse<BoInquiryDetailDto.Response> findInquirie(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(new BoInquiryDetailDto.Response());
    }

}
