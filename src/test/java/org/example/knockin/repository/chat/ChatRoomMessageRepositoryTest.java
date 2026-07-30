package org.example.knockin.repository.chat;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.knockin.config.QueryDslConfig;
import org.example.knockin.dto.MessageType;
import org.example.knockin.entity.auth.LoginProviderType;
import org.example.knockin.entity.chat.ChatRoomMember;
import org.example.knockin.entity.chat.ChatRoomMessage;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingRequiredStatus;
import org.example.knockin.entity.chat.ChattingRoom;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberRole;
import org.example.knockin.repository.chat.row.ChatRoomUnreadCountRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(QueryDslConfig.class)
@DisplayName("채팅 메시지 Repository")
class ChatRoomMessageRepositoryTest {

    @Autowired
    private ChatRoomMessageRepository chatRoomMessageRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("안 읽은 메시지 수는 상대방 및 시스템 메시지만 집계한다")
    void findUnreadMessageCountsExcludesReadAndOwnMessages() {
        // Given
        Member viewer = persistMember("viewer-unread-count");
        Member opponent = persistMember("opponent-unread-count");
        ChattingRoom room = persistChattingRoom(viewer, opponent);
        persistChatRoomMember(room, viewer);
        persistChatRoomMember(room, opponent);

        persistChatRoomMessage(room, opponent, "이미 읽은 메시지", true);
        persistChatRoomMessage(room, opponent, "안 읽은 상대방 메시지", false);
        persistChatRoomMessage(room, viewer, "내가 보낸 메시지", false);
        persistChatRoomMessage(room, null, "안 읽은 시스템 메시지", false);

        entityManager.flush();
        entityManager.clear();

        // When
        List<ChatRoomUnreadCountRow> counts = chatRoomMessageRepository.findUnreadMessageCounts(
                viewer.getId(),
                List.of(room.getId())
        );

        // Then
        assertThat(counts).singleElement()
                .satisfies(count -> {
                    assertThat(count.chatRoomId()).isEqualTo(room.getId());
                    assertThat(count.messageCount()).isEqualTo(2L);
                });
    }

    private Member persistMember(String providerId) {
        Member member = Member.builder()
                .providerType(LoginProviderType.KAKAO)
                .providerId(providerId)
                .role(MemberRole.USER)
                .isDelete(false)
                .build();
        entityManager.persist(member);
        return member;
    }

    private ChattingRoom persistChattingRoom(Member requester, Member requestee) {
        ChattingRequired chattingRequired = ChattingRequired.builder()
                .requester(requester)
                .requestee(requestee)
                .status(ChattingRequiredStatus.ACCEPTED)
                .build();
        entityManager.persist(chattingRequired);

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .chattingRequired(chattingRequired)
                .build();
        entityManager.persist(chattingRoom);
        return chattingRoom;
    }

    private void persistChatRoomMember(ChattingRoom chattingRoom, Member member) {
        entityManager.persist(ChatRoomMember.builder()
                .chattingRoom(chattingRoom)
                .member(member)
                .isLeft(false)
                .build());
    }

    private void persistChatRoomMessage(
            ChattingRoom chattingRoom,
            Member member,
            String contents,
            Boolean isRead
    ) {
        entityManager.persist(ChatRoomMessage.builder()
                .chattingRoom(chattingRoom)
                .member(member)
                .type(MessageType.TEXT)
                .contents(contents)
                .isRead(isRead)
                .build());
    }
}
