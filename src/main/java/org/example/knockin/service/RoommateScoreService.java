package org.example.knockin.service;

import java.util.List;
import java.util.Map;
import org.example.knockin.dto.Compatibility;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingScore;
import org.example.knockin.entity.room.MyRoommate;
import org.example.knockin.entity.room.RoommateScore;

public interface RoommateScoreService {
    Map<Long, Compatibility> calculateScores(Long requesterId, List<Long> targetMemberIds);

    Map<Long, Integer> calculateSimpleScores(Long requesterId, List<Long> targetMemberIds);

    Compatibility calculateScore(Long requesterId, Long targetMemberId);

    Integer calculateSimpleScore(Long requesterId, Long targetMemberId);

    List<ChattingScore> createChattingScores(ChattingRequired chattingRequired);

    List<RoommateScore> createRoommateScores(MyRoommate myRoommate);

    Compatibility calculateChattingCompatibility(Long memberId, List<ChattingScore> chattingScores);

    Compatibility calculateRoommateCompatibility(Long memberId, List<RoommateScore> roommateScores);
}
