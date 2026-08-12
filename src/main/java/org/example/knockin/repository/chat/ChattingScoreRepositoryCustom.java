package org.example.knockin.repository.chat;

import java.util.Optional;
import org.example.knockin.entity.chat.ChattingScore;

public interface ChattingScoreRepositoryCustom {
    Optional<ChattingScore> findOneByChattingRequiredIdAndMemberId(Long chattingRequiredId, Long memberId);
}