package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.StateMachineHelper;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class Retrying implements Status {
    @Override
    public void applyToAwaitingRetry(Project project) {
        StateMachineHelper.projectNeedARetryScheduled(project);
        project.setStatus(ProjectStatusEnum.AWAITING_RETRY);
    }

    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        StateMachineHelper.projectCannotHaveARetryScheduled(project);
        StateMachineHelper.projectNeedTechnicalVisitScheduled(this, project);

        project.setStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT);
    }

    @Override
    public void applyToAwaitingMaterials(Project project) {
        StateMachineHelper.projectCannotHaveARetryScheduled(project);

        project.setStatus(ProjectStatusEnum.AWAITING_MATERIALS);
    }

    @Override
    public void applyToScheduledInstallingVisit(Project project) {
        StateMachineHelper.projectCannotHaveARetryScheduled(project);
        StateMachineHelper.projectNeedHaveInstallVisitScheduled(this, project);

        project.setStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT);
    }

    @Override
    public void applyToNegotiationFailed(Project project) {
        StateMachineHelper.projectCannotHaveARetryScheduled(project);
        project.setStatus(ProjectStatusEnum.NEGOTIATION_FAILED);
    }
}
