package org.example.knockin.service.impl;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.knockin.entity.member.Member;
import org.example.knockin.exception.AlarmErrorCode;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmServiceImpl {
    private final FirebaseMessaging firebaseMessaging;

    public void sendByMember(Member member) {
        String fcmToken = member.getFcmToken();
        send("테스트", "테스트",  fcmToken);
    }

    public void send(String title, String body, String fcmToken) {
        try {
            String response = firebaseMessaging.send(Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("title", title)
                    .putData("body", body)
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder().setChannelId("default_channel_id").setPriority(AndroidNotification.Priority.HIGH).setDefaultSound(true).setDefaultVibrateTimings(true).build())
                            .build())
                    .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").setContentAvailable(true).build())
                            .build())
                    .setToken(fcmToken)
                    .build());
            log.info("Successfully send Notification: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error(
                    "FCM 전송 실패: messagingErrorCode={}, errorCode={}, message={}",
                    e.getMessagingErrorCode(),
                    e.getErrorCode(),
                    e.getMessage(),
                    e
            );
            throw new BusinessException(AlarmErrorCode.PUSH_ALARM_SEND_FAIL);
        }
    }

    public void send(String title, String body, String fcmToken, String deepLinkInfo) {
        try {
            String response = firebaseMessaging.send(Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("title", title)
                    .putData("body", body)
                    .putData("deep_link", deepLinkInfo)
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder().setChannelId("default_channel_id").setPriority(AndroidNotification.Priority.HIGH).setDefaultSound(true).setDefaultVibrateTimings(true).build())
                            .build())
                    .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").setContentAvailable(true).build())
                            .build())
                    .setToken(fcmToken)
                    .build());
            log.info("Successfully send Notification: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error(
                    "FCM 전송 실패: messagingErrorCode={}, errorCode={}, message={}",
                    e.getMessagingErrorCode(),
                    e.getErrorCode(),
                    e.getMessage(),
                    e
            );
            throw new BusinessException(AlarmErrorCode.PUSH_ALARM_SEND_FAIL);
        }
    }
}
