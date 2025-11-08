package com.solarize.solarizeWebBackend.modules.project.state;

public interface Status {
    void applyToNew(StatusContext context);

    void applyToPreBudget(StatusContext context);

    void applyToClientAwaitingContact(StatusContext context);

    void applyToAwaitingRetry(StatusContext context);

    void applyToRetrying(StatusContext context);

    void applyToScheduledTechnicalVisit(StatusContext context);

    void applyToTechnicalVisitCompleted(StatusContext context);

    void applyToFinalBudget(StatusContext context);

    void applyToAwaitingMaterials(StatusContext context);

    void applyToScheduledInstallingVisit(StatusContext context);

    void applyToInstalled(StatusContext context);

    void applyToCompleted(StatusContext context);

    void applyToNegociationFailed(StatusContext context);
}
