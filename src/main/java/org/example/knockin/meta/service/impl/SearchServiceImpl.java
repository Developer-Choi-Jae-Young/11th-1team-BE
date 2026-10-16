package org.example.knockin.meta.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.meta.dto.PopularSearchDto;
import org.example.knockin.member.entity.Member;
import org.example.knockin.member.repository.MemberRepository;
import org.example.knockin.meta.entity.Search;
import org.example.knockin.meta.repository.SearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl {
    private final SearchRepository searchRepository;
    private final MemberRepository memberRepository;

    public List<PopularSearchDto.Response.RankItem> findPopSearch() {
        return searchRepository.findPopSearch();
    }

    @Transactional
    public Search save(Long memberId, String keyword) {
        Member member = memberRepository.getReferenceById(memberId);
        Search search = Search.builder()
                .member(member)
                .keyword(keyword.trim())
                .build();

        return searchRepository.save(search);
    }
}
