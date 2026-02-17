package com.solarize.solarizeWebBackend.modules.project.dto.request;

import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUpdateDto {
    @Size(min = 1, max = 60)
    private String name;
    private Long clientId;
    private Long responsibleId;
    @Size(max = 1000)
    private String description;
    private SystemTypeEnum projectType;
}
