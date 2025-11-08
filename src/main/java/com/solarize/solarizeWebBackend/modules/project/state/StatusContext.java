package com.solarize.solarizeWebBackend.modules.project.state;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class StatusContext {
    @Setter
    private Status state;
    private Project project;

    public void apply(ProjectStatusEnum status) {
        switch (status) {
            case NEW -> state.applyToNew(this);
            case PRE_BUDGET -> state.applyToPreBudget(this);
            case CLIENT_AWAITING_CONTACT -> state.applyToClientAwaitingContact(this);
            case AWAITING_RETRY -> state.applyToAwaitingRetry(this);
            case RETRYING -> state.applyToRetrying(this);
            case SCHEDULED_TECHNICAL_VISIT -> state.applyToScheduledTechnicalVisit(this);
            case TECHNICAL_VISIT_COMPLETED -> state.applyToTechnicalVisitCompleted(this);
            case FINAL_BUDGET -> state.applyToFinalBudget(this);
            case AWAITING_MATERIALS -> state.applyToAwaitingMaterials(this);
            case SCHEDULED_INSTALLING_VISIT -> state.applyToScheduledInstallingVisit(this);
            case INSTALLED -> state.applyToInstalled(this);
            case COMPLETED -> state.applyToCompleted(this);
            case NEGOTIATION_FAILED -> state.applyToNegociationFailed(this);
        }
    }
}
