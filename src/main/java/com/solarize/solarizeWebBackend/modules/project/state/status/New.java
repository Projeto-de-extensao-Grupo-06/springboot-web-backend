package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.project.state.StatusContext;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransition;

public class New implements Status {
    @Override
    public void applyToNew(StatusContext context) {}

    @Override
    public void applyToPreBudget(StatusContext context) {
        throw new InvalidStateTransition("It is not possible to change the status from NEW to PRE_BUDGET.");
    }

    @Override
    public void applyToClientAwaitingContact(StatusContext context) {
        throw new InvalidStateTransition("It is not possible to change the status from NEW to CLIENT_AWAITING_CONTACT.");
    }

    @Override
    public void applyToAwaitingRetry(StatusContext context) {
        context.setState(new AwaitingRetry());
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
