package org.example.knockin.repository.chat;

import java.util.List;
import org.example.knockin.dto.ChatRoomListDto;

public interface ChattingRoomRepositoryCustom {
    List<ChatRoomListDto.Response> findByMemberId(Long memberId);

    boolean existsActiveRoomBetweenMembers(Long memberAId, Long memberBId);

    long countActiveRoomsByMemberId(Long memberId);
}
