package org.example.knockin.service;

import java.util.List;
import java.util.Map;
import org.example.knockin.dto.Compatibility;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingScore;
import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.PreferenceConditionWeight;
import org.example.knockin.entity.room.MyRoommate;
import org.example.knockin.entity.room.RoommateScore;

public abstract class RoommateScoreService {
    public abstract Map<Long, Compatibility> calculateScores(Long requesterId, List<Long> targetMemberIds);

    public abstract Map<Long, Integer> calculateSimpleScores(Long requesterId, List<Long> targetMemberIds);

    public abstract Compatibility calculateScore(Long requesterId, Long targetMemberId);

    public abstract Integer calculateSimpleScore(Long requesterId, Long targetMemberId);

    public abstract List<ChattingScore> createChattingScores(ChattingRequired chattingRequired);

    public abstract List<RoommateScore> createRoommateScores(MyRoommate myRoommate);

    public abstract Compatibility calculateChattingCompatibility(Long memberId, List<ChattingScore> chattingScores);

    public abstract Compatibility calculateRoommateCompatibility(Long memberId, List<RoommateScore> roommateScores);

    public Long calculateScores(List<LifePattern> me, List<LifePattern> target, List<PreferenceConditionWeight> preferenceConditionWeightList) {
        return 100L;
    }

    public double calculateScores(LifePattern me, LifePattern target) {
        return 100f;
    }
}
