package org.example.knockin.repository.chat;

import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRequiredRepository extends JpaRepository<ChattingRequired, Long>, ChattingRequiredRepositoryCustom {
    Page<ChattingRequired> findByRequestee(Member requestee, Pageable pageable);
}
