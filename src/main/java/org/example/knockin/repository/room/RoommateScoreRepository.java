package org.example.knockin.repository.room;

import java.util.List;
import org.example.knockin.entity.room.MyRoommate;
import org.example.knockin.entity.room.RoommateScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoommateScoreRepository extends JpaRepository<RoommateScore, Long>, RoommateScoreRepositoryCustom {
    List<RoommateScore> findByMyRoommate(MyRoommate myRoommate);

    List<RoommateScore> findByMyRoommateId(Long myRoommateId);
}