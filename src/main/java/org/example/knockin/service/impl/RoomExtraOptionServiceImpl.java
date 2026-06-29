package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.RoomExtraOption;
import org.example.knockin.repository.room.RoomExtraOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomExtraOptionServiceImpl {
    private final RoomExtraOptionRepository roomExtraOptionRepository;

    public List<RoomExtraOption> findAllById(List<Long> ids) {
        return roomExtraOptionRepository.findAllById(ids);
    }

    public List<RoomExtraOption> findAllByIsDeleted(boolean isDeleted) {
        return roomExtraOptionRepository.findAllByIsDeleted(isDeleted);
    }
}
