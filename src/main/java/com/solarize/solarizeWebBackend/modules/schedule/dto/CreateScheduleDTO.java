package com.solarize.solarizeWebBackend.modules.schedule.dto;


import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class CreateScheduleDTO {
    @NotBlank(message = "The title is obligatory")
    private String title;

    private String description;

    @NotNull(message = "The start date is obligatory")
    @FutureOrPresent(message = "The start date can´t be in the past")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "The start date can´t be in the past")
    private LocalDateTime endDate;

    @NotNull(message = "The type is obligatory")
    private ScheduleTypeEnum type;

    private Long coworkerId;

    private Long projectId;
    
    private LocalTime notificationAlertTime;

}
