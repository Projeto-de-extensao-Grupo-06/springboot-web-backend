package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class Installed implements Status {
    @Override
    public void applyToCompleted(Project project) {


        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.COMPLETED);
    }
}
