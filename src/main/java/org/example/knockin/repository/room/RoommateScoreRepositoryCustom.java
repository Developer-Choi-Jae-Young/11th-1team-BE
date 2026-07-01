package org.example.knockin.repository.room;

import java.util.List;
import org.example.knockin.entity.room.RoommateScore;

public interface RoommateScoreRepositoryCustom {
    List<RoommateScore> findWithScoreDetailsByMyRoommateId(Long myRoommateId);
}
