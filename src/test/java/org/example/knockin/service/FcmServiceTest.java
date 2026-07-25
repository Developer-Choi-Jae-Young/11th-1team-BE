package org.example.knockin.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.example.knockin.entity.member.Member;
import org.example.knockin.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
