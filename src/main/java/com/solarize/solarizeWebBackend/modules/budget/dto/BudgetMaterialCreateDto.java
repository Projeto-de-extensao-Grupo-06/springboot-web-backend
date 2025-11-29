package com.solarize.solarizeWebBackend.modules.budget.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BudgetMaterialCreateDto {
    @Valid
    @NotNull
    @Positive
    private Long materialId;

    @Valid
    @NotNull
    @Positive
    private Integer quantity;
}
