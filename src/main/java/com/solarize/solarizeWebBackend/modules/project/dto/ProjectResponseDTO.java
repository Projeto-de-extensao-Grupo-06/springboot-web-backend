package com.solarize.solarizeWebBackend.modules.project.dto;

import com.solarize.solarizeWebBackend.modules.project.ProjectSourceEnum;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String description;
    private ProjectStatusEnum projectStatusEnum;
    private Integer statusWeight;
    private boolean isActive;
    private SystemTypeEnum systemTypeEnum;
    private ProjectSourceEnum projectSourceEnum;
    private Long clientId;
    private Long addressId;
    private Long budgetId;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;

}
