package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilsController {
    @GetMapping("/terms")
    public CommonResponse<?> findTermList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/terms/{termsId}")
    public CommonResponse<?> findTerm(@PathVariable Long termsId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/search/popular")
    public CommonResponse<?> findPopSearch() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/meta/lifestyle-patterns")
    public CommonResponse<?> findLifeStylePatterns() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/meta/room-types")
    public CommonResponse<?> findRoomTypes() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/meta/regions")
    public CommonResponse<?> findRegions() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/meta/room-add-options")
    public CommonResponse<?> findRoomAddOptions() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
