package org.example.knockin.repository.chat;

import java.util.List;
import org.example.knockin.repository.chat.row.ChatRoomListRow;

public interface ChattingRoomRepositoryCustom {
    List<ChatRoomListRow> findListRowsByMemberId(Long memberId);

    boolean existsActiveRoomBetweenMembers(Long memberAId, Long memberBId);

    long countActiveRoomsByMemberId(Long memberId);
}
