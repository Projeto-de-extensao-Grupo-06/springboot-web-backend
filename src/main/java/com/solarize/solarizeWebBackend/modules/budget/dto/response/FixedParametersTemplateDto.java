package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixedParametersTemplateDto {
    private FixedParameterName name;
    private ParameterValueType type;
}
