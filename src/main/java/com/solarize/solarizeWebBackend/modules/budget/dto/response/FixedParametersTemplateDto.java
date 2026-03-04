package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixedParametersTemplateDto {
    private String name;
    private ParameterValueType type;
}
