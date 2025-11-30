package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateFixedParameterDto {
    @NotNull
    private FixedParameterName parameterName;
    @PositiveOrZero
    private Double value;
}
