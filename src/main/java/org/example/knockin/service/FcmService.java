package org.example.knockin.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.knockin.entity.member.Member;
import org.example.knockin.service.impl.MemberServiceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    private final MemberServiceImpl memberServiceImpl;

    public void sendByMember(Long memberId) {
        Member member = memberServiceImpl.findByIdOrThrow(memberId);
        String fcmToken = member.getFcmToken();
        sendNotification("테스트", "테스트",  fcmToken, "");
    }

    public void sendNotification(String title, String body, String fcmToken, String deepLink) {
        log.info("Attempting to send Notification (title: {}, body: {}, fcmToken: {})", title, body, fcmToken);
        send(createMessage(title, body, fcmToken, deepLink));
    }

    private void send(Message message) {
        try {
            String response = firebaseMessaging.send(message);
            log.info("Successfully send Notification: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send Notification : {}", e.getMessage());
        }
    }

    private Message createMessage(String title, String body, String fcmToken, String deepLink) {
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(fcmToken)
                .putData("deepLink", deepLink)
                .build();
    }
}
