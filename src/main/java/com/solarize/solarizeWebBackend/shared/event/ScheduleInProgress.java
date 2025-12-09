package com.solarize.solarizeWebBackend.shared.event;

import java.time.LocalDateTime;

public record ScheduleInProgress(
        Long scheduleId,
        LocalDateTime startDate,
        LocalDateTime endDate
) { }
