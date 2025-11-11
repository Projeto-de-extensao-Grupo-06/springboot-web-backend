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

public class ScheduledInstallingVisit implements Status {
    @Override
    public void applyToRetrying(Project project) {
        List<Schedule> schedules = project.getSchedules().stream().filter(
                schedule ->
                        schedule.getType() == ScheduleTypeEnum.INSTALL_VISIT &&
                        schedule.getDate().isAfter(LocalDateTime.now()) &&
                        schedule.getStatus() == ScheduleStatusEnum.MARKED &&
                        schedule.getIsActive()
        ).toList();

        if(!schedules.isEmpty()) {
            throw new InvalidStateTransitionException("Cancel the install visit before retry.");
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.RETRYING);
    }

    @Override
    public void applyToInstalled(Project project) {
        List<Schedule> schedules = project.getSchedules().stream().filter(schedule ->
                schedule.getType() == ScheduleTypeEnum.INSTALL_VISIT &&
                        schedule.getDate().isBefore(LocalDateTime.now()) &&
                        schedule.getStatus() == ScheduleStatusEnum.FINISHED
        ).toList();

        if(schedules.isEmpty()) {
            throw new InvalidStateTransitionException("Project don't have a FINISHED INSTALL_VISIT schedule");
        }

        project.setPreviewStatus(project.getStatus());
        project.setStatus(ProjectStatusEnum.INSTALLED);
    }
}
