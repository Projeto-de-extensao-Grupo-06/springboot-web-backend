package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterOptionCreateDto {

    @NotBlank
    private String type;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double additionTax;

    @NotNull
    @DecimalMin("0.0")
    private Double fixedCost;
}