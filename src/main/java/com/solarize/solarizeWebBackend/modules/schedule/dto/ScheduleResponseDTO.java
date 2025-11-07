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
    private LocalDateTime date;
    private Boolean isActive;
    private Long projectId;
    private String type;
    private Long coworkerId;
    private String status;
    private String title;
    private String description;

}
