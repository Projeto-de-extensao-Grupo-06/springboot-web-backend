package com.solarize.solarizeWebBackend.shared.event;

import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;

import java.time.LocalDateTime;

public record ScheduleCreatedEvent(
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleStatusEnum status,
        ScheduleTypeEnum type,
        Long projectId
) {
}
