package org.example.knockin.repository.chat;

import java.util.List;
import java.util.Optional;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.chat.row.ChatRequestListRow;

public interface ChattingRequiredRepositoryCustom {
    boolean existsBetweenMembers(Member memberA, Member memberB);

    Optional<ChattingRequired> findLatest(Member memberA, Member memberB);

    List<ChatRequestListRow> findAllPendingByRequestee(Member requestee);
}
