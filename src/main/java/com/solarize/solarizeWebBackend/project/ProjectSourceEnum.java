package com.solarize.solarizeWebBackend.project;

public enum ProjectSourceEnum {
    SITE("site"),
    BOT("bot");

    public String value;

    ProjectSourceEnum(String value) {
        this.value = value;
    }
}
