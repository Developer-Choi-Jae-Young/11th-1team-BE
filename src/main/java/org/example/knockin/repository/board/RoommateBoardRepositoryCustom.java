package org.example.knockin.repository.board;

import jakarta.annotation.Nullable;
import java.util.List;
import org.example.knockin.dto.BoardListDto;
import org.example.knockin.entity.member.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoommateBoardRepositoryCustom {
    Page<BoardListDto.Response> search(
            @Nullable List<Long> regionIds,
            @Nullable List<Long> roomTypeIds,
            @Nullable Gender gender,
            @Nullable Integer minDeposit,
            @Nullable Integer maxDeposit,
            @Nullable Integer minMounthRent,
            @Nullable Integer maxMounthRent,
            Pageable pageable
    );
}
