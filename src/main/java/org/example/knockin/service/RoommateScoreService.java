package org.example.knockin.service;

import java.util.List;
import java.util.Map;
import org.example.knockin.dto.Compatibility;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingScore;
import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.entity.life.PreferenceConditionWeight;

public abstract class RoommateScoreService {
    public abstract Map<Long, Compatibility> calculateScores(Long requesterId, List<Long> targetMemberIds);

    public abstract Map<Long, Integer> calculateSimpleScores(Long requesterId, List<Long> targetMemberIds);

    public abstract Compatibility calculateScore(Long requesterId, Long targetMemberId);

    public abstract Integer calculateSimpleScore(Long requesterId, Long targetMemberId);

    public abstract List<ChattingScore> createChattingScores(ChattingRequired chattingRequired);

    public Compatibility calculateScores(List<LifePatternInformation> me, List<LifePatternInformation> target, List<PreferenceConditionWeight> preferenceConditionWeightList) {
        return Compatibility.builder().build();
    }

    public Compatibility.LifeStyleInfo calculateScores(LifePatternInformation me, LifePatternInformation target, LifePattern lifePattern) {
        return Compatibility.LifeStyleInfo.builder().build();
    }
}
