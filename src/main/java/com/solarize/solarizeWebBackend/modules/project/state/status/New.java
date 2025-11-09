package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class New implements Status {
    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT);
    }
}
