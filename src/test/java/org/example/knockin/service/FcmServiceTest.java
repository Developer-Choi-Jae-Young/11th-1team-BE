package org.example.knockin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.Map;
import org.example.knockin.entity.member.Member;
import org.example.knockin.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("FCM 서비스 테스트")
class FcmServiceTest {

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @Mock
    private MemberServiceImpl memberService;

    @InjectMocks
    private FcmService fcmService;

    @Test
    @DisplayName("회원에게 알림을 보낼 때 저장된 FCM 토큰을 사용한다")
    void sendByMemberUsesStoredFcmToken() throws Exception {
        // given
        Long memberId = 1L;
        Member member = mock(Member.class);
        given(memberService.findByIdOrThrow(memberId)).willReturn(member);
        given(member.getFcmToken()).willReturn("fcm-token");
        given(firebaseMessaging.send(any(Message.class))).willReturn("message-id");

        // when
        fcmService.sendByMember(memberId);

        // then
        verify(member).getFcmToken();
        verify(firebaseMessaging).send(any(Message.class));
    }

    @Test
    @DisplayName("푸시 알림을 보낼 때 notification과 딥링크 데이터에 전달받은 값을 설정한다")
    void sendNotificationBuildsNotificationAndDeepLinkData() throws Exception {
        // given
        given(firebaseMessaging.send(any(Message.class))).willReturn("message-id");

        // when
        fcmService.sendNotification(
                "매칭 요청이 수락됐어요",
                "이수현님과의 룸메이트가 이뤄졌어요.",
                "fcm-token",
                "knockinrn://chat/10"
        );

        // then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(firebaseMessaging).send(messageCaptor.capture());

        Message message = messageCaptor.getValue();
        assertThat(ReflectionTestUtils.getField(message, "token")).isEqualTo("fcm-token");

        Notification notification = (Notification) ReflectionTestUtils.getField(message, "notification");
        assertThat(notification).isNotNull();
        assertThat(ReflectionTestUtils.getField(notification, "title")).isEqualTo("매칭 요청이 수락됐어요");
        assertThat(ReflectionTestUtils.getField(notification, "body"))
                .isEqualTo("이수현님과의 룸메이트가 이뤄졌어요.");

        assertThat((Map<String, String>) ReflectionTestUtils.getField(message, "data"))
                .isEqualTo(Map.of("deepLink", "knockinrn://chat/10"));
    }
}
