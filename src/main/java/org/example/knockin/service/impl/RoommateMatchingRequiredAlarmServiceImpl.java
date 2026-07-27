package org.example.knockin.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.knockin.global.entity.RoommateRequiredMessageTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.example.knockin.entity.alarm.AlarmType;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.room.RoommateMatchingRequired;
import org.example.knockin.entity.room.RoommateMatchingRequiredAlarm;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoommateMatchingRequiredAlarmServiceImpl {
    private final BasicInformationServiceImpl basicInformationService;
    private final AlarmServiceImpl alarmService;
    @Value("${policy.request-alarm.expire-days}")
    private int requestAlarmExpireDays;

    public void send(Member receiver, String title, String contents, RoommateMatchingRequired required) {
        RoommateMatchingRequiredAlarm alarm = RoommateMatchingRequiredAlarm.builder()
                .member(receiver)
                .title(title)
                .contents(contents)
                .expiredAt(LocalDateTime.now().plusDays(requestAlarmExpireDays))
                .type(AlarmType.ROOM_MATCHING)
                .roommateMatchingRequired(required)
                .build();

        alarmService.sendToClient(receiver.getId(), AlarmType.ROOM_MATCHING.name(), alarm);
    }
}
