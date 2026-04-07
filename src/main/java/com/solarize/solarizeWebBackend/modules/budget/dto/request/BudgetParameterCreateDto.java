package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BudgetParameterCreateDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String metric;

    @NotNull
    private Boolean isPreBudget;

    @NotNull
    @DecimalMin(value = "0.0", message = "Base value cannot be negative.")
    private Double fixedValue;

    @Valid
    @NotNull
    private List<ParameterOptionCreateDto> options = new ArrayList<>();
}