package org.example.knockin.controller;

import org.example.knockin.dto.MetaLifestylePatternsDto;
import org.example.knockin.dto.MetaRegionsDto;
import org.example.knockin.dto.MetaRoomAddOptionsDto;
import org.example.knockin.dto.MetaRoomTypesDto;
import org.example.knockin.dto.PopularSearchDto;
import org.example.knockin.dto.TermsDetailDto;
import org.example.knockin.dto.TermsListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilsController {
    @GetMapping("/terms")
    public CommonResponse<TermsListDto.Response> findTermList() {
        return CommonResponse.status(HttpStatus.OK).body(new TermsListDto.Response());
    }

    @GetMapping("/terms/{termsId}")
    public CommonResponse<TermsDetailDto.Response> findTerm(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(new TermsDetailDto.Response());
    }

    @GetMapping("/search/popular")
    public CommonResponse<PopularSearchDto.Response> findPopSearch() {
        return CommonResponse.status(HttpStatus.OK).body(new PopularSearchDto.Response());
    }

    @GetMapping("/meta/lifestyle-patterns")
    public CommonResponse<MetaLifestylePatternsDto.Response> findLifeStylePatterns() {
        return CommonResponse.status(HttpStatus.OK).body(new MetaLifestylePatternsDto.Response());
    }

    @GetMapping("/meta/room-types")
    public CommonResponse<MetaRoomTypesDto.Response> findRoomTypes() {
        return CommonResponse.status(HttpStatus.OK).body(new MetaRoomTypesDto.Response());
    }

    @GetMapping("/meta/regions")
    public CommonResponse<MetaRegionsDto.Response> findRegions() {
        return CommonResponse.status(HttpStatus.OK).body(new MetaRegionsDto.Response());
    }

    @GetMapping("/meta/room-add-options")
    public CommonResponse<MetaRoomAddOptionsDto.Response> findRoomAddOptions() {
        return CommonResponse.status(HttpStatus.OK).body(new MetaRoomAddOptionsDto.Response());
    }
}
