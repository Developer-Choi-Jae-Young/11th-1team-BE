package org.example.knockin.repository.chat.impl;

import static org.example.knockin.entity.chat.QChatRoomFile.chatRoomFile;
import static org.example.knockin.entity.chat.QChatRoomMessage.chatRoomMessage;
import static org.example.knockin.entity.file.QFile.file;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.ChatRoomDetailDto.ChatMessage;
import org.example.knockin.entity.chat.QChatRoomMember;
import org.example.knockin.entity.chat.QChatRoomMessage;
import org.example.knockin.repository.chat.ChatRoomMessageRepositoryCustom;
import org.example.knockin.repository.chat.row.ChatRoomUnreadCountRow;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomMessageRepositoryImpl implements ChatRoomMessageRepositoryCustom {
    public final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatMessage> findChatMessageDto(Long chatRoomId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatMessage.class,
                        chatRoomMessage.id,
                        chatRoomMessage.member.id,
                        chatRoomMessage.contents,
                        chatRoomMessage.createdAt,
                        chatRoomMessage.type,
                        file.savedFileName
                ))
                .from(chatRoomMessage)
                .leftJoin(chatRoomFile)
                .on(chatRoomFile.chatRoomMessage.eq(chatRoomMessage))
                .leftJoin(chatRoomFile.file, file)
                .where(chatRoomMessage.chattingRoom.id.eq(chatRoomId))
                .orderBy(chatRoomMessage.createdAt.asc(), chatRoomMessage.id.asc())
                .fetch();
    }

    @Override
    public List<ChatRoomUnreadCountRow> findUnreadMessageCounts(Long memberId, List<Long> chatRoomIds) {
        if (chatRoomIds.isEmpty()) return List.of();

        QChatRoomMember viewerRoomMember = new QChatRoomMember("unreadViewerRoomMember");
        QChatRoomMessage unreadMessage = new QChatRoomMessage("unreadMessage");

        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatRoomUnreadCountRow.class,
                        unreadMessage.chattingRoom.id,
                        unreadMessage.count()
                ))
                .from(unreadMessage)
                .join(viewerRoomMember)
                .on(
                        viewerRoomMember.chattingRoom.eq(unreadMessage.chattingRoom),
                        viewerRoomMember.member.id.eq(memberId),
                        viewerRoomMember.isLeft.isFalse()
                )
                .where(
                        unreadMessage.chattingRoom.id.in(chatRoomIds),
                        unreadMessage.isRead.isFalse(),
                        unreadMessage.member.isNull().or(unreadMessage.member.id.ne(memberId))
                )
                .groupBy(unreadMessage.chattingRoom.id)
                .fetch();
    }
}
