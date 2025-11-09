package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class FinalBudget implements Status {
    @Override
    public void applyToAwaitingRetry(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.AWAITING_RETRY);
    }

    @Override
    public void applyToAwaitingMaterials(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.AWAITING_MATERIALS);
    }
}
