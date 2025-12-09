package com.solarize.solarizeWebBackend.modules.project.dto.request;

import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectManualCreateDto {
    @NotBlank
    @Size(min = 1, max = 60)
    private String name;

    @NotNull
    private Long clientId;

    @Size(max = 1000)
    private String description;

    private Long addressId;

    @NotNull
    private SystemTypeEnum projectType;
}