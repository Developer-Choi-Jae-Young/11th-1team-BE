package org.example.knockin.repository.chat;

import java.util.List;
import org.example.knockin.entity.chat.ChattingScore;

public interface ChattingScoreRepositoryCustom {
    List<ChattingScore> findWithScoreDetailsByChattingRequiredIdAndMemberId(Long chattingRequiredId, Long memberId);
}