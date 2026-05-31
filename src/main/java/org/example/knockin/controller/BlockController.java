package org.example.knockin.controller;

import org.example.knockin.dto.BlockDto;
import org.example.knockin.dto.BlockListDto;
import org.example.knockin.global.api.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blocks")
public class BlockController {
    @PostMapping("")
    public CommonResponse<BlockDto.Response> saveBlock(@RequestBody BlockDto.Request request) {
        return CommonResponse.status(HttpStatus.OK).body(new BlockDto.Response());
    }

    @GetMapping("")
    public CommonResponse<BlockListDto.Response> findBlockList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponse.status(HttpStatus.OK).body(new BlockListDto.Response());
    }


    @DeleteMapping("/{blockId}")
    public CommonResponse<BlockDto.Response> deleteBlock(@PathVariable Long blockId) {
        return CommonResponse.status(HttpStatus.OK).body(new BlockDto.Response());
    }
}
