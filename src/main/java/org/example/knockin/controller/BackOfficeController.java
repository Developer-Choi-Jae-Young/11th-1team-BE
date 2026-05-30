package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bo")
public class BackOfficeController {
    @PostMapping("/terms")
    public CommonResponse<?> saveTerms() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/terms/{termsId}/draft")
    public CommonResponse<?> modifyTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/terms")
    public CommonResponse<?> findTermsList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/terms/{termsId}")
    public CommonResponse<?> findTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/terms/{termsId}")
    public CommonResponse<?> deleteTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/terms/{termsId}/publish")
    public CommonResponse<?> modifyLastTerms(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/room-types")
    public CommonResponse<?> saveRoomType() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/room-types")
    public CommonResponse<?> findRoomTypeList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/room-types/{id}")
    public CommonResponse<?> modifyRoomType(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/room-types/{id}")
    public CommonResponse<?> deleteRoomType(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/room-types/{id}")
    public CommonResponse<?> findRoomType(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/lifestyle-patterns")
    public CommonResponse<?> findLifeStylePatternList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/lifestyle-patterns/{id}")
    public CommonResponse<?> findLifeStylePattern(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/lifestyle-patterns")
    public CommonResponse<?> saveLifeStylePattern() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/lifestyle-patterns/{id}")
    public CommonResponse<?> modifyLifeStylePattern(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/lifestyle-patterns/{id}")
    public CommonResponse<?> deleteLifeStylePattern(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/verifications/company")
    public CommonResponse<?> findVerificationsCompanyList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/verifications/company/{id}/approve")
    public CommonResponse<?> saveVerificationsCompany(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/notices")
    public CommonResponse<?> saveNotice() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/notices")
    public CommonResponse<?> findNoticeList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/notices/{id}")
    public CommonResponse<?> findNotice(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/notices/{id}")
    public CommonResponse<?> modifyNotice(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/notices/{id}")
    public CommonResponse<?> deleteNotice(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/inquiries")
    public CommonResponse<?> saveInquiries() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/inquiries")
    public CommonResponse<?> findInquirieList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/inquiries/{id}")
    public CommonResponse<?> findInquirie(@PathVariable Long id) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
