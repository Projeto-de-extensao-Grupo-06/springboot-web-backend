package com.solarize.solarizeWebBackend.modules.project.state.status;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.state.Status;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

import java.time.LocalDateTime;
import java.util.List;

public class ClientAwaitingContact implements Status {
    @Override
    public void applyToAwaitingRetry(Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule -> schedule.getIsActive() && schedule.getDate().isAfter(LocalDateTime.now()))
                .toList();

        if(!schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + this.getClass().getSimpleName() + " -> AWAITING_RETRY. " +
                            "The project have an active schedule. Please cancel it before updating the state."
            );
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.AWAITING_RETRY);
    }

    @Override
    public void applyToScheduledTechnicalVisit(Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule -> schedule.getIsActive() &&
                        schedule.getDate().isAfter(LocalDateTime.now()) &&
                        schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT)
                .toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + this.getClass().getSimpleName() + " -> SCHEDULED_TECHNICAL_VISIT. " +
                            "The project does not have an active TECHNICAL_VISIT schedule. Please create it before updating the state."
            );
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.SCHEDULED_TECHNICAL_VISIT);
    }
}
