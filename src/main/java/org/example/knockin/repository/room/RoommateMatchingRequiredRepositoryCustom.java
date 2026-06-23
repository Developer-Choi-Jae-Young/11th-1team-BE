package org.example.knockin.repository.room;

import java.util.Optional;
import org.example.knockin.entity.room.RoommateMatchingRequired;

public interface RoommateMatchingRequiredRepositoryCustom {
    Optional<RoommateMatchingRequired> findLatest(Long chattingRoomId);
}
