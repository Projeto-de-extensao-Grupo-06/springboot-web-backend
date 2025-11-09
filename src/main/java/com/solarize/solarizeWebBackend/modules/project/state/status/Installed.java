package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class Installed implements Status {
    @Override
    public void applyToCompleted(Project project) {
        Status.super.applyToCompleted(project);
    }
}
