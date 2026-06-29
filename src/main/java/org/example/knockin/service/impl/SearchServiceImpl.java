package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.PopularSearchDto;
import org.example.knockin.repository.member.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl {
    private final SearchRepository searchRepository;

    public List<PopularSearchDto.Response.RankItem> findPopSearch() {
        return searchRepository.findPopSearch();
    }
}
