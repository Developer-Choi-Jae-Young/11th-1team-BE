package org.example.knockin.repository.chat;

import java.util.List;
import org.example.knockin.entity.chat.ChattingScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingScoreRepository extends JpaRepository<ChattingScore, Long>, ChattingScoreRepositoryCustom {
    List<ChattingScore> findByChattingRequiredId(Long chattingRequiredId);
}