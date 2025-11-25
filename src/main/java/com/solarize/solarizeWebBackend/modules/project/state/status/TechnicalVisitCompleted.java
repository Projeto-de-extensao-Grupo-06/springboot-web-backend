package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.budget.core.Budget;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

public class TechnicalVisitCompleted implements Status {
    @Override
    public void applyToFinalBudget(Project project) {
        Budget budget = project.getBudget();

        if(budget == null) throw new InvalidStateTransitionException("Project doesn't have a budget.");

        if(!budget.getFinalBudget()) throw new InvalidStateTransitionException("Budget is not a final budget.");

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.FINAL_BUDGET);
    }
}
