package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.StateMachineHelper;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

import java.time.LocalDateTime;
import java.util.List;

public class PreBudget implements Status {
    @Override
    public void applyToClientAwaitingContact(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT);
    }

    @Override
    public void applyToAwaitingRetry(Project project) {
        StateMachineHelper.projectNeedARetryScheduled(project);

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.AWAITING_RETRY);
    }
}
