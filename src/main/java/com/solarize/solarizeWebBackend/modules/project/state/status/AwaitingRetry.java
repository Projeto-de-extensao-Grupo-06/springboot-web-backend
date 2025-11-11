package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;

public class AwaitingRetry implements Status {
    @Override
    public void applyToRetrying(Project project) {
        /*
         * This transition cannot set a previous project status,
         * because AWAITING_RETRY is an intermediate state.
         * Setting this as a previous status would break the tracking
         * of the project's previous state.
         */

        project.setStatus(ProjectStatusEnum.RETRYING);
    }
}
