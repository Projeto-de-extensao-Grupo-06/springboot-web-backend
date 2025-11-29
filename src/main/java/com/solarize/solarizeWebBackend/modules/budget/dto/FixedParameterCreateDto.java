package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedParameterCreateDto {
    @Valid
    @NotNull
    private FixedParameterName parameterName;

    @Valid
    @PositiveOrZero
    @NotNull
    private Double value;
}
