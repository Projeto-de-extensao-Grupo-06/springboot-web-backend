package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.project.state.StatusContext;

public class ClientAwaitingContact implements Status {
    @Override
    public void applyToNew(StatusContext context) {

    }

    @Override
    public void applyToPreBudget(StatusContext context) {

    }

    @Override
    public void applyToClientAwaitingContact(StatusContext context) {

    }

    @Override
    public void applyToAwaitingRetry(StatusContext context) {

    }

    @Override
    public void applyToRetrying(StatusContext context) {

    }

    @Override
    public void applyToScheduledTechnicalVisit(StatusContext context) {

    }

    @Override
    public void applyToTechnicalVisitCompleted(StatusContext context) {

    }

    @Override
    public void applyToFinalBudget(StatusContext context) {

    }

    @Override
    public void applyToAwaitingMaterials(StatusContext context) {

    }

    @Override
    public void applyToScheduledInstallingVisit(StatusContext context) {

    }

    @Override
    public void applyToInstalled(StatusContext context) {

    }

    @Override
    public void applyToCompleted(StatusContext context) {

    }

    @Override
    public void applyToNegociationFailed(StatusContext context) {

    }
}
