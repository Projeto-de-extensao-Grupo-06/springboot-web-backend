package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduledTechnicalVisit implements Status {
    @Override
    public void applyToRetrying(Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule -> schedule.getIsActive() && schedule.getStartDate().isAfter(LocalDateTime.now()))
                .toList();

        if(!schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + this.getClass().getSimpleName() + " -> RETRYING. " +
                            "The project has an active schedule. Please cancel it before updating the state."
            );
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.RETRYING);
    }

    @Override
    public void applyToTechnicalVisitCompleted(Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule ->
                        schedule.getStatus() == ScheduleStatusEnum.FINISHED &&
                        schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT)
                .toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + this.getClass().getSimpleName() + " -> TECHNICAL_VISIT_COMPLETED. " +
                            "The project does not have an TECHNICAL_VISIT finished."
            );
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.TECHNICAL_VISIT_COMPLETED);
    }
}
