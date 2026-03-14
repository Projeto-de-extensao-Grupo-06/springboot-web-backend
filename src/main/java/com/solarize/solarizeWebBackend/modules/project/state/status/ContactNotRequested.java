package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class ContactNotRequested implements Status {
    @Override
    public void applyToPreBudget(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.PRE_BUDGET);
    }

    @Override
    public void applyToClientAwaitingContact(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.CLIENT_AWAITING_CONTACT);
    }

    @Override
    public void applyToNegotiationFailed(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.NEGOTIATION_FAILED);
    }
}
