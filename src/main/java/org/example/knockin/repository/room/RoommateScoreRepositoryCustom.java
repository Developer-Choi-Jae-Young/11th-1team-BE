package org.example.knockin.repository.room;

import java.util.Optional;
import org.example.knockin.entity.room.RoommateScore;

public interface RoommateScoreRepositoryCustom {
    Optional<RoommateScore> findOneByMyRoommateIdAndMemberId(Long myRoommateId, Long memberId);
}
