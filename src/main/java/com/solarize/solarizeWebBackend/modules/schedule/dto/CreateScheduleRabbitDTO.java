package com.solarize.solarizeWebBackend.modules.schedule.dto;

import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;

import java.time.LocalDateTime;

public record CreateScheduleRabbitDTO(
        Long scheduleId,
        String projectTitle,
        String email,
        String phone,
        ScheduleTypeEnum type,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}