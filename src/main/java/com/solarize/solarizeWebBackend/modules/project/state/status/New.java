package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

import java.time.LocalDateTime;
import java.util.List;

public class New implements Status {
    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        List<Schedule> schedules = project.getSchedules().stream().filter(
                schedule ->
                        schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT &&
                                schedule.getStartDate().isAfter(LocalDateTime.now()) &&
                                schedule.getStatus() == ScheduleStatusEnum.MARKED &&
                                schedule.getIsActive()
        ).toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException("You cannot apply NEW to SCHEDULED_TECHNICAL_VISIT without a TECHNICAL_VISIT scheduled");
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT);
    }

    @Override
    public void applyToFinalBudget(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.FINAL_BUDGET);
    }

    @Override
    public void applyToPreBudget(Project project) {
        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.PRE_BUDGET);
    }
}
