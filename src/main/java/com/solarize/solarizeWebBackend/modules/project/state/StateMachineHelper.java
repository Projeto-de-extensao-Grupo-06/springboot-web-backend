package com.solarize.solarizeWebBackend.modules.project.state;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueue;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;

import java.time.LocalDateTime;
import java.util.List;

public class StateMachineHelper {
    public static void projectNeedTechnicalVisitScheduled(Status context, Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule -> schedule.getIsActive() &&
                        schedule.getStartDate().isAfter(LocalDateTime.now()) &&
                        schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT &&
                        schedule.getStatus() == ScheduleStatusEnum.MARKED
                )
                .toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + context.getClass().getSimpleName() + " -> SCHEDULED_TECHNICAL_VISIT. " +
                            "The project does not have an active TECHNICAL_VISIT schedule. Please create it before updating the state."
            );
        }
    }

    public static void projectNeedHaveInstallVisitScheduled(Status context, Project project) {
        if(project.getSchedules() == null) {
            throw new IllegalStateException("Project schedules not initialized");
        }

        List<Schedule> schedules = project.getSchedules()
                .stream()
                .filter(schedule -> schedule.getIsActive() &&
                        schedule.getStartDate().isAfter(LocalDateTime.now()) &&
                        schedule.getType() == ScheduleTypeEnum.INSTALL_VISIT &&
                        schedule.getStatus() == ScheduleStatusEnum.MARKED
                )
                .toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException(
                    "Invalid state transition: " + context.getClass().getSimpleName() + " -> SCHEDULED_INSTALLING_VISIT. " +
                            "The project does not have an active INSTALL_VISIT schedule. Please create it before updating the state."
            );
        }
    }

    public static void projectCannotHaveARetryScheduled(Project project) {
        if(project == null) throw new InvalidStateTransitionException("Project cannot be null");

        RetryQueue retryQueue = project.getRetry();
        if(retryQueue != null) throw new InvalidStateTransitionException("The project cannot have a scheduled retry.");
    }

    public static void projectNeedARetryScheduled(Project project) {
        if(project == null) throw new InvalidStateTransitionException("Project cannot be null");

        RetryQueue retryQueue = project.getRetry();
        if(retryQueue == null) throw new InvalidStateTransitionException("The project needs to have a scheduled retry.");
        if(retryQueue.getRetrying()) throw new InvalidStateTransitionException("The project cannot be retrying.");
        if(retryQueue.getScheduledDate().isBefore(LocalDateTime.now())) throw new InvalidStateTransitionException("The retry need to be scheduled in future datetime");
    }
}
