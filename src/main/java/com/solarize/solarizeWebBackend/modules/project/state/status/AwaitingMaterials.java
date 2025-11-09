package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class AwaitingMaterials implements Status {
    @Override
    public void applyToScheduledInstallingVisit(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.SCHEDULED_INSTALLING_VISIT);
    }
}
