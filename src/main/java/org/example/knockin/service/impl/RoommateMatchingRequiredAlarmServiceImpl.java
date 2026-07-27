package org.example.knockin.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.RoommateRequiredStatus;
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
    // TODO: 제목-본문 고정값 관리 방식 고민 필요
    private static final Map<RoommateRequiredStatus, String> TITLE_TEMPLATES = Map.of(
            RoommateRequiredStatus.PENDING, "%s님의 매칭 요청",
            RoommateRequiredStatus.ACCEPTED, "매칭 요청이 수락됐어요",
            RoommateRequiredStatus.REJECTED, "매칭 요청 거절"
    );

    private static final Map<RoommateRequiredStatus, String> CONTENTS_TEMPLATES = Map.of(
            RoommateRequiredStatus.PENDING, "%s님이 매칭을 요청했어요.",
            RoommateRequiredStatus.ACCEPTED, "%s님과의 룸메이트가 이뤄졌어요.",
            RoommateRequiredStatus.REJECTED, "%s님이 매칭 요청을 거절했어요."
    );

    private final BasicInformationServiceImpl basicInformationService;
    private final AlarmServiceImpl alarmService;
    @Value("${policy.request-alarm.expire-days}")
    private int requestAlarmExpireDays;

    public void send(Member receiver, Member sender, RoommateMatchingRequired required) {
        BasicInformation basicInformation = basicInformationService.findLatestBasicInformation(sender);
        String senderName = basicInformation.getName();

        String title = String.format(TITLE_TEMPLATES.get(required.getStatus()), senderName);
        String contents = String.format(CONTENTS_TEMPLATES.get(required.getStatus()), senderName);

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
