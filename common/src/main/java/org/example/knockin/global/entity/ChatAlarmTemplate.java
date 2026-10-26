package org.example.knockin.global.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatAlarmTemplate implements AlarmMessageTemplate {
    MESSAGE("%s", "%s", "knockinrn://chat/%s"),
    ;

    private final String title;
    private final String contents;
    private final String deepLink;

    public String formatTitle(Object... args) {
        return String.format(this.title, args);
    }

    public String formatContents(Object... args) {
        return String.format(this.contents, args);
    }

    public String formatDeepLink(Object... args) {
        return String.format(this.deepLink, args);
    }
}
