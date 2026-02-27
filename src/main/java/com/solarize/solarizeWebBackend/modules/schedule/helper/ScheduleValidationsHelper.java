package com.solarize.solarizeWebBackend.modules.schedule.helper;

import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.BaseException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ScheduleValidationsHelper {
    public static Optional<BaseException> scheduleValidation(List<Schedule> currentSchedules, Schedule schedule) {
        boolean technicalVisit = currentSchedules.stream()
                .anyMatch(s -> s.getType() == ScheduleTypeEnum.TECHNICAL_VISIT);

        boolean installVisit = currentSchedules.stream()
                .anyMatch(s -> s.getType() == ScheduleTypeEnum.INSTALL_VISIT);

        boolean hasOverlap = currentSchedules.stream().anyMatch(
                existingSchedule -> {
                    LocalDateTime start1 = existingSchedule.getStartDate();
                    LocalDateTime end1 = existingSchedule.getEndDate();
                    LocalDateTime start2 = schedule.getStartDate();
                    LocalDateTime end2 = schedule.getEndDate();

                    if (end1 == null || end2 == null) return false;

                    return start1.isBefore(end2) && end1.isAfter(start2);
                });

        if (hasOverlap) {
            return Optional.of(new ConflictException("The selected date and time range overlaps with an existing schedule for this coworker. " +
                    "Use force to do this."));
        }

        if(schedule.getType() == ScheduleTypeEnum.TECHNICAL_VISIT && installVisit) {
            return Optional.of(new ConflictException("A past or future installation visit already exists for this project. " +
                    "This operation does not make sense. Resubmit the request with the force flag if you really want to perform this operation."));
        }

        if(schedule.getType() == ScheduleTypeEnum.INSTALL_VISIT && !technicalVisit) {
            return Optional.of(new ConflictException("An installation visit is being created without a technical visit ever having been created. " +
                    "This operation does not make sense. If you really want to do this, use the force flag."));
        }

        return Optional.empty();
    }
}
