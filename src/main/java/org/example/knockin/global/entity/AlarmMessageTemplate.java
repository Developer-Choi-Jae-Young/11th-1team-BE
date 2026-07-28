package org.example.knockin.global.entity;

public interface AlarmMessageTemplate {
    String formatTitle(Object... args);
    String formatContents(Object... args);
    String formatDeepLink(Object... args);
}
