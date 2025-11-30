package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class UpdateMaterialDto {
    @NotEmpty
    @NotNull
    private List<BudgetMaterialCreateDto> materials;

    @Data
    public static class BudgetMaterialCreateDto {
        @Valid
        @NotNull
        @Positive
        private Long materialId;

        @Valid
        @NotNull
        @Positive
        private Integer quantity;
    }
}
