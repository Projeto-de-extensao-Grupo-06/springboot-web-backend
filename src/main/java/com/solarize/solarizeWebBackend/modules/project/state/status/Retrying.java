package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class Retrying implements Status {
    @Override
    public void applyToNew(Project project) {
        Status.super.applyToNew(project);
    }

    @Override
    public void applyToPreBudget(Project project) {
        Status.super.applyToPreBudget(project);
    }

    @Override
    public void applyToClientAwaitingContact(Project project) {
        Status.super.applyToClientAwaitingContact(project);
    }

    @Override
    public void applyToAwaitingRetry(Project project) {
        Status.super.applyToAwaitingRetry(project);
    }

    @Override
    public void applyToRetrying(Project project) {
        Status.super.applyToRetrying(project);
    }

    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        Status.super.applyToScheduledTechnicalVisit(project);
    }

    @Override
    public void applyToTechnicalVisitCompleted(Project project) {
        Status.super.applyToTechnicalVisitCompleted(project);
    }

    @Override
    public void applyToFinalBudget(Project project) {
        Status.super.applyToFinalBudget(project);
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
    public void applyToInstalled(Project project) {
        Status.super.applyToInstalled(project);
    }

    @Override
    public void applyToCompleted(Project project) {
        Status.super.applyToCompleted(project);
    }

    @Override
    public void applyToNegociationFailed(Project project) {
        Status.super.applyToNegociationFailed(project);
    }
}
