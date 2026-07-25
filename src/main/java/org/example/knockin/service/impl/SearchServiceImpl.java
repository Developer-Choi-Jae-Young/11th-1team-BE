package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.PopularSearchDto;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.Search;
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

    public Search save(Member member, String keyword) {
        Search search = Search.builder()
                .member(member)
                .keyword(keyword.trim())
                .build();

        return searchRepository.save(search);
    }
}
