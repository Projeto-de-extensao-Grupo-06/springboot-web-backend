package com.solarize.solarizeWebBackend.modules.material.dto;

import com.solarize.solarizeWebBackend.modules.material.enumerated.MetricEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MaterialResponseDto {
    private Long id;
    private String name;
    private String description;
    private MetricEnum metric;
}
