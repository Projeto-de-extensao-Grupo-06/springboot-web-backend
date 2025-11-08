package com.solarize.solarizeWebBackend.modules.schedule.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponseDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String status;
    private Boolean isActive;
    private Long projectId;
    private Long coworkerId;

}
