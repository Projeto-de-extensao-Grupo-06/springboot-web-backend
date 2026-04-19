package com.solarize.solarizeWebBackend.modules.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelScheduleDTO {
    private Long scheduleId;
}
