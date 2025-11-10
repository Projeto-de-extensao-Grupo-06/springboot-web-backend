package com.solarize.solarizeWebBackend.modules.project;

public enum ProjectStatusEnum {

    NEW(10,"new"),
    PRE_BUDGET(20,"pre_budget"),
    CLIENT_AWAITING_CONTACT(30,"client_awaiting_contact"),
    AWAITING_RETRY(40,"awaiting_retry"),
    RETRYING(50,"retrying"),
    SCHEDULED_TECHNICAL_VISIT(60,"scheduled_technical_visit"),
    TECHNICAL_VISIT_COMPLETED(70,"technical_visit_completed"),
    FINAL_BUDGET(80,"final_budget"),
    AWAITING_MATERIALS(90,"awaiting_materials"),
    SCHEDULED_INSTALLING_VISIT(100,"scheduled_installing_visit"),
    INSTALLED(110,"installed"),
    COMPLETED(120,"completed"),
    NEGOTIATION_FAILED(130,"negotiation_failed");

    private final int weight;
    private final String value;

    ProjectStatusEnum(int weight, String value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight(){
        return weight;
    }

    public String getValue(){
        return value;
    }

}
