package com.solarize.solarizeWebBackend.modules.project;

public enum SystemTypeEnum {
    ON_GRID("on-grid"),
    OFF_GRID("off-grid");

    public String value;

    SystemTypeEnum(String value) {
        this.value = value;
    }
}
