package org.example.knockin.repository.chat;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.example.knockin.config.QueryDslConfig;
import org.example.knockin.dto.ChatRoomListDto;
import org.example.knockin.entity.auth.LoginProviderType;
import org.example.knockin.entity.chat.ChatRoomMember;
import org.example.knockin.entity.chat.ChatRoomMemberId;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingRoom;
import org.example.knockin.entity.file.BasicInformationFile;
import org.example.knockin.entity.file.File;
import org.example.knockin.entity.file.FileType;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Gender;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@ActiveProfiles("test")
@Import(QueryDslConfig.class)
@DisplayName("채팅방 Repository")
class ChattingRoomRepositoryTest {

    private static final LocalDateTime CHAT_ROOM_CREATED_AT = LocalDateTime.of(2026, 6, 18, 12, 0);

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("회원이 참여 중인 채팅방 목록은 상대 회원 정보와 채팅방 상태를 반환하고 나간 방은 제외한다")
    void findByMemberIdReturnsActiveRoomsWithOtherMemberInfo() {
        // Given
        Member viewer = persistMember("viewer");
        Member activeOpponent = persistMember("active-opponent");
        Member leftOpponent = persistMember("left-opponent");
        Member unrelatedMember = persistMember("unrelated");
        persistBasicInformationWithProfile(activeOpponent, "상대회원", "opponent-profile.jpg");
        persistBasicInformationWithProfile(leftOpponent, "나간방상대", "left-profile.jpg");
        persistBasicInformationWithProfile(unrelatedMember, "무관회원", "unrelated-profile.jpg");

        ChattingRoom activeRoom = persistChattingRoom(viewer, activeOpponent, true);
        persistChatRoomMember(activeRoom, viewer, false);
        persistChatRoomMember(activeRoom, activeOpponent, false);

        ChattingRoom leftRoom = persistChattingRoom(viewer, leftOpponent, true);
        persistChatRoomMember(leftRoom, viewer, true);
        persistChatRoomMember(leftRoom, leftOpponent, false);

        ChattingRoom unrelatedRoom = persistChattingRoom(activeOpponent, unrelatedMember, false);
        persistChatRoomMember(unrelatedRoom, activeOpponent, false);
        persistChatRoomMember(unrelatedRoom, unrelatedMember, false);

        entityManager.flush();
        entityManager.clear();

        // When
        List<ChatRoomListDto.Response> responses = chattingRoomRepository.findByMemberId(viewer.getId());

        // Then
        assertThat(responses).hasSize(1);
        ChatRoomListDto.Response response = responses.getFirst();
        assertThat(response.getChatRoomId()).isEqualTo(activeRoom.getId());
        assertThat(response.getMemberName()).isEqualTo("상대회원");
        assertThat(response.getMemberProfileImageUrl()).isEqualTo("opponent-profile.jpg");
        assertThat(response.getCreatedAt()).isEqualTo(CHAT_ROOM_CREATED_AT);
        assertThat(response.getIsAgree()).isTrue();
    }

    @Test
    @DisplayName("참여 중인 채팅방이 없으면 빈 목록을 반환한다")
    void findByMemberIdReturnsEmptyListWhenMemberHasNoActiveRooms() {
        // Given
        Member viewer = persistMember("viewer-empty");
        Member opponent = persistMember("opponent-empty");
        ChattingRoom room = persistChattingRoom(viewer, opponent, true);
        persistChatRoomMember(room, viewer, true);
        persistChatRoomMember(room, opponent, false);
        entityManager.flush();
        entityManager.clear();

        // When
        List<ChatRoomListDto.Response> responses = chattingRoomRepository.findByMemberId(viewer.getId());

        // Then
        assertThat(responses).isEmpty();
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

    private BasicInformation persistBasicInformationWithProfile(Member member, String name, String savedFileName) {
        BasicInformation basicInformation = BasicInformation.builder()
                .member(member)
                .name(name)
                .birth(LocalDate.of(2000, 1, 1))
                .gender(Gender.MALE)
                .email(name + "@example.com")
                .build();
        entityManager.persist(basicInformation);

        File file = File.builder()
                .type(FileType.USER_PROFILE_IMAGE)
                .originalFileName(savedFileName)
                .savedFileName(savedFileName)
                .fileExt("jpg")
                .isDeleted(false)
                .build();
        entityManager.persist(file);

        BasicInformationFile basicInformationFile = newBasicInformationFile(basicInformation, file);
        entityManager.persist(basicInformationFile);
        return basicInformation;
    }

    private BasicInformationFile newBasicInformationFile(BasicInformation basicInformation, File file) {
        try {
            Constructor<BasicInformationFile> constructor = BasicInformationFile.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            BasicInformationFile basicInformationFile = constructor.newInstance();
            ReflectionTestUtils.setField(basicInformationFile, "basicInformation", basicInformation);
            ReflectionTestUtils.setField(basicInformationFile, "file", file);
            return basicInformationFile;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("기본 정보 파일 테스트 엔티티 생성에 실패했습니다.", e);
        }
    }

    private ChattingRoom persistChattingRoom(Member requester, Member requestee, Boolean isAgree) {
        ChattingRequired chattingRequired = ChattingRequired.builder()
                .requester(requester)
                .requestee(requestee)
                .isAgree(isAgree)
                .build();
        entityManager.persist(chattingRequired);

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .chattingRequired(chattingRequired)
                .build();
        entityManager.persist(chattingRoom);
        entityManager.flush();
        entityManager.createNativeQuery("update chatting_room set created_at = ? where id = ?")
                .setParameter(1, Timestamp.valueOf(CHAT_ROOM_CREATED_AT))
                .setParameter(2, chattingRoom.getId())
                .executeUpdate();
        return chattingRoom;
    }

    private ChatRoomMember persistChatRoomMember(ChattingRoom chattingRoom, Member member, Boolean isLeft) {
        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .id(new ChatRoomMemberId(chattingRoom.getId(), member.getId()))
                .chattingRoom(chattingRoom)
                .member(member)
                .isLeft(isLeft)
                .build();
        entityManager.persist(chatRoomMember);
        return chatRoomMember;
    }
}
