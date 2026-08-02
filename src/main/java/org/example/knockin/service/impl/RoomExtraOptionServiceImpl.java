package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.MetaRoomAddOptionsDto;
import org.example.knockin.entity.file.BasicInformationFile;
import org.example.knockin.entity.file.File;
import org.example.knockin.entity.room.RoomExtraOption;
import org.example.knockin.entity.room.RoomExtraOptionFile;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.RoomTypeErrorCode;
import org.example.knockin.repository.room.RoomExtraOptionFileRepository;
import org.example.knockin.repository.room.RoomExtraOptionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomExtraOptionServiceImpl {
    private final RoomExtraOptionRepository roomExtraOptionRepository;
    private final RoomExtraOptionFileRepository roomExtraOptionFileRepository;

    public List<RoomExtraOption> findAllById(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return roomExtraOptionRepository.findAllById(ids);
    }

    public List<MetaRoomAddOptionsDto.Response.RoomAddOptionItem> findAllByIsDeleted(boolean isDeleted) {
        return roomExtraOptionRepository.findAllByIsDeleted(isDeleted);
    }

    public List<RoomExtraOption> findRoomExtraOptionList(Pageable pageable) {
        return roomExtraOptionRepository.findAll(pageable).stream().toList();
    }

    @Transactional
    public RoomExtraOption modifyRoomExtraOption(RoomExtraOption roomExtraOption, Long id) {
        RoomExtraOption roomExtraOptionEntity = roomExtraOptionRepository.findById(id).orElseThrow(() -> new BusinessException(RoomTypeErrorCode.ROOM_EXTRA_OPTION_NOT_FOUND));
        roomExtraOptionEntity.modifyRoomExtraOption(roomExtraOption);
        return roomExtraOptionEntity;
    }

    @Transactional
    public RoomExtraOption saveRoomExtraOption(RoomExtraOption roomExtraOption) {
        return roomExtraOptionRepository.save(roomExtraOption);
    }

    @Transactional
    public RoomExtraOptionFile saveRoomExtraOptionFile(RoomExtraOptionFile roomExtraOptionFile) {
        return roomExtraOptionFileRepository.save(roomExtraOptionFile);
    }

    public RoomExtraOptionFile findRoomExtraOptionFile(RoomExtraOption roomExtraOption) {
        return roomExtraOptionFileRepository.findByRoomExtraOption(roomExtraOption).orElse(null);
    }

    @Transactional
    public RoomExtraOption deleteRoomExtraOption(Long id) {
        RoomExtraOption roomExtraOption = roomExtraOptionRepository.findById(id).orElseThrow(() -> new BusinessException(RoomTypeErrorCode.ROOM_EXTRA_OPTION_NOT_FOUND));
        roomExtraOption.deleteRoomExtraOption();
        return roomExtraOption;
    }

    public RoomExtraOption findRoomAddOptions(Long id) {
        return roomExtraOptionRepository.findById(id).orElseThrow(() -> new BusinessException(RoomTypeErrorCode.ROOM_EXTRA_OPTION_NOT_FOUND));
    }
}
