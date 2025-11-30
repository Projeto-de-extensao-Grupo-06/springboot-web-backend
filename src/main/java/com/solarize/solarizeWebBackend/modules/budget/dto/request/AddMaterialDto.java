package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class AddMaterialDto {
    @Valid
    @NotNull
    @Positive
    private Long materialId;

    @Valid
    @NotNull
    @Positive
    private Integer quantity;
}
