package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.project.state.status.*;
import lombok.Getter;

@Getter
public enum ProjectStatusEnum {

    NEW(3, new New()),
    PRE_BUDGET(4, new PreBudget()),
    CLIENT_AWAITING_CONTACT(1, new ClientAwaitingContact()),
    AWAITING_RETRY(11, new AwaitingRetry()),
    RETRYING(2, new Retrying()),
    SCHEDULED_TECHNICAL_VISIT(5, new ScheduledTechnicalVisit()),
    TECHNICAL_VISIT_COMPLETED(8, new TechnicalVisitCompleted()),
    FINAL_BUDGET(7, new FinalBudget()),
    AWAITING_MATERIALS(9, new AwaitingMaterials()),
    SCHEDULED_INSTALLING_VISIT(6, new ScheduledInstallingVisit()),
    INSTALLED(10, new Installed()),
    COMPLETED(13, new Completed()),
    NEGOTIATION_FAILED(12, new NegotiationFailed());

    private final Integer weight;
    private final Status state;

    ProjectStatusEnum(Integer weight, Status state) {
        this.weight = weight;
        this.state = state;
    }

    public int getWeight(){
        return weight;
    }

    public String getValue(){
        return value;
    }

}
