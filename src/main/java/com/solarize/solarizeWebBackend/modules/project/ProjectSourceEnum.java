package com.solarize.solarizeWebBackend.modules.project;

public enum ProjectSourceEnum {
    SITE_BUDGET_FORM("SITE_BUDGET_FORM"),
    WHATSAPP_BOT("WHATSAPP_BOT"),
    INTERNAL_MANUAL_ENTRY("INTERNAL_MANUAL_ENTRY");


    public String value;

    ProjectSourceEnum(String value) {
        this.value = value;
    }
}
