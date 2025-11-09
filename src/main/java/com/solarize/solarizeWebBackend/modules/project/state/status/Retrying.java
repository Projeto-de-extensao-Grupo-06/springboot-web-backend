package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class Retrying implements Status {
    @Override
    public void applyToAwaitingRetry(Project project) {
        Status.super.applyToAwaitingRetry(project);
    }

    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        Status.super.applyToScheduledTechnicalVisit(project);
    }

    @Override
    public void applyToAwaitingMaterials(Project project) {
        Status.super.applyToAwaitingMaterials(project);
    }

    @Override
    public void applyToScheduledInstallingVisit(Project project) {
        Status.super.applyToScheduledInstallingVisit(project);
    }

    @Override
    public void applyToNegociationFailed(Project project) {
        Status.super.applyToNegociationFailed(project);
    }
}
