package com.solarize.solarizeWebBackend.modules.material.dto;

import com.solarize.solarizeWebBackend.modules.material.enumerated.MetricEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequestDto {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private MetricEnum metric;
}
