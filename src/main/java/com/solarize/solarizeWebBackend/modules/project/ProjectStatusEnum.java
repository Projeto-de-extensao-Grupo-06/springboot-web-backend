package com.solarize.solarizeWebBackend.modules.project;

public enum ProjectStatusEnum {

    NEW("new"),
    PRE_BUDGET("pre_budget"),
    CLIENT_AWAITING_CONTACT("client_awaiting_contact"),
    AWAITING_RETRY("awaiting_retry"),
    RETRYING("retrying"),
    SCHEDULED_TECHNICAL_VISIT("scheduled_technical_visit"),
    TECHNICAL_VISIT_COMPLETED("technical_visit_completed"),
    FINAL_BUDGET("final_budget"),
    AWAITING_MATERIALS("awaiting_materials"),
    SCHEDULED_INSTALLING_VISIT("scheduled_installing_visit"),
    INSTALLED("installed"),
    COMPLETED("completed"),
    NEGOTIATION_FAILED("negotiation_failed");


    public String value;

    ProjectStatusEnum(String value) {
        this.value = value;
    }
}
