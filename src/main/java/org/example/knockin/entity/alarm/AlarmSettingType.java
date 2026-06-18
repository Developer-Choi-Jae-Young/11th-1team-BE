package org.example.knockin.entity.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmSettingType {
    NOTIFICATION("알림");

    private final String message;
}