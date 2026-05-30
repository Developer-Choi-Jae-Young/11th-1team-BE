package org.example.knockin.controller;

import org.example.knockin.global.api.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blocks")
public class BlockController {
    @PostMapping("/")
    public CommonResponse<?> saveBlock() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/")
    public CommonResponse<?> findBlockList() {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{blockId}")
    public CommonResponse<?> deleteBlock(@PathVariable Long blockId) {
        return CommonResponse.status(HttpStatus.OK).body(null);
    }
}
