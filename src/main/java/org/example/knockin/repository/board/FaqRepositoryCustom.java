package org.example.knockin.repository.board;

import org.example.knockin.dto.FaqAllListDto;
import org.example.knockin.dto.FaqListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FaqRepositoryCustom {
    List<FaqListDto.Response.FaqInfo> findFaqList(Pageable pageable);
    List<FaqAllListDto.Response.FaqInfo> findFaqAllList(Pageable pageable);
}
