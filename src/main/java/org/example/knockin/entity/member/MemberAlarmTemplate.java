package org.example.knockin.entity.member;

import lombok.RequiredArgsConstructor;
import org.example.knockin.global.entity.AlarmMessageTemplate;

@RequiredArgsConstructor
public enum MemberAlarmTemplate implements AlarmMessageTemplate {
    MEMBER_ACTIVE("계정 정지가 해제되었어요", "계정 정지가 해제되었습니다. 서비스를 정상적으로 이용하실 수 있습니다.", "myapp://main"),
    ;

    private final String title;
    private final String contents;
    private final String deepLink;

    @Override
    public String formatTitle(Object... args) {
        return String.format(this.title, args);
    }

    @Override
    public String formatContents(Object... args) {
        return String.format(this.contents, args);
    }

    @Override
    public String formatDeepLink(Object... args) {
        return String.format(this.deepLink, args);
    }
}
