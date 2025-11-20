package com.solarize.solarizeWebBackend.modules.schedule.dto;


import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class CreateScheduleDTO {

    @NotBlank(message = "The title is obligatory")
    private String title;

    private String description;

    @NotNull(message = "The start date is obligatory")
    @FutureOrPresent(message = "The start date can´t be in the past")
    private LocalDateTime startDate;

    @NotNull(message = "The end date is obligatory")
    @FutureOrPresent(message = "The end date can´t be in the past")
    private LocalDateTime endDate;

    @NotNull(message = "The type is obligatory")
    private ScheduleTypeEnum type;

    @NotNull(message = "The status is obligatory")
    private ScheduleStatusEnum status;

    @NotNull(message = "The coworker ID is obligatory")
    private Long coworkerId;

    private Boolean isActive = true;

    private LocalTime notificationAlertTime;

}
