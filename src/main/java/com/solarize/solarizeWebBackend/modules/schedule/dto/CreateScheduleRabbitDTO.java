package com.solarize.solarizeWebBackend.modules.schedule.dto;

import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

public record CreateScheduleRabbitDTO(
        Long scheduleId,
        String projectTitle,
        String title,
        List<RecipientDTO> recipients,
        ScheduleTypeEnum type,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}