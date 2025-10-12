package com.solarize.solarizeWebBackend.modules.schedule;

public enum ScheduleStatusEnum {
    MARKED("marked"),
    IN_PROGRESS("in_progress"),
    FINISHED("finished");

    public String value;

    ScheduleStatusEnum(String value) {
        this.value = value;
    }
}
