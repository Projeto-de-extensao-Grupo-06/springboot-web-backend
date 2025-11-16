package com.solarize.solarizeWebBackend.modules.project.state;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

public interface Status {
    default void applyToNew(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> NEW");
    }

    default void applyToPreBudget(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> PRE_BUDGET");
    }

    default void applyToClientAwaitingContact(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> CLIENT_AWAITING_CONTACT");
    }

    default void applyToAwaitingRetry(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> AWAITING_RETRY");
    }

    default void applyToRetrying(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> RETRYING");
    }

    default void applyToScheduledTechnicalVisit(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> SCHEDULED_TECHNICAL_VISIT");
    }

    default void applyToTechnicalVisitCompleted(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> TECHNICAL_VISIT_COMPLETED");
    }

    default void applyToFinalBudget(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> FINAL_BUDGET");
    }

    default void applyToAwaitingMaterials(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> AWAITING_MATERIALS");
    }

    default void applyToScheduledInstallingVisit(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> SCHEDULED_INSTALLING_VISIT");
    }

    default void applyToInstalled(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> INSTALLED");
    }

    default void applyToCompleted(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> COMPLETED");
    }

    default void applyToNegotiationFailed(Project project) {
        throw new InvalidStateTransitionException("Invalid state transition: " + this.getClass().getSimpleName() + " -> NEGOTIATION_FAILED");
    }

}
