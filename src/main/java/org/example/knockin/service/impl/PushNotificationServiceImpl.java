package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.alarm.AlarmSettingType;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.alarm.AlarmSettingRepository;
import org.example.knockin.service.FcmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl {
    private final AlarmSettingRepository alarmSettingRepository;
    private final FcmService fcmService;

    public void send(Member receiver, AlarmSettingType alarmSettingType, String title, String body, String deepLink) {
        String fcmToken = receiver.getFcmToken();
        if (!StringUtils.hasText(fcmToken)) return;
        boolean isEnabled = alarmSettingRepository.existsByMemberAndAlarmSettingTypeAndIsEnabledTrue(receiver, alarmSettingType);
        if (!isEnabled) return;
        fcmService.sendNotification(title, body, fcmToken, deepLink);
    }
}
