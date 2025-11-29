package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class PersonalizedParameterDto {
    @Valid
    @NotBlank
    private String name;

    @Valid
    @NotBlank
    private ParameterValueType type;

    @Valid
    @PositiveOrZero
    private Double value;
}
