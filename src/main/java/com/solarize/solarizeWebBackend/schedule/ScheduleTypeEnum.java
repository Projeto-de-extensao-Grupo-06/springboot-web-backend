package com.solarize.solarizeWebBackend.schedule;

public enum ScheduleTypeEnum {
    VISIT("visit"),
    NOTE("note");

    public String value;

    ScheduleTypeEnum(String value) {
        this.value = value;
    }
}
