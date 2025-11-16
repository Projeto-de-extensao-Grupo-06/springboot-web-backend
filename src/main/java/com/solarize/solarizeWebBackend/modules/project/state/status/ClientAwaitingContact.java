package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.StateMachineHelper;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class ClientAwaitingContact implements Status {
    @Override
    public void applyToAwaitingRetry(Project project) {
        StateMachineHelper.projectNeedARetryScheduled(project);

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.AWAITING_RETRY);
    }

    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        StateMachineHelper.projectNeedTechnicalVisitScheduled(this, project);

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT);
    }
}
