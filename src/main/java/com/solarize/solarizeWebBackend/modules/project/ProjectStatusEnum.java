package com.solarize.solarizeWebBackend.modules.project;

public enum ProjectStatusEnum {
    PRE_BUDGET("pre_budget"),
    SCHEDULED_TECHNICAL_VISIT("scheduled_technical_visit"),
    VISITING("visiting"),
    FINAL_BUDGET("final_budget"),
    INSTALING("instaling"),
    INSTALLING_AND_HOMOLOGATING("installing and homologating"),
    HOMOLOGATED("homologated"),
    FINISHED("finished");

    public String value;

    ProjectStatusEnum(String value) {
        this.value = value;
    }
}
