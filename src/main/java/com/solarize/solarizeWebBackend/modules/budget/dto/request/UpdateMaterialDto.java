package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class UpdateMaterialDto {
    @Valid
    @NotNull
    private List<BudgetMaterialDto> materials;

    @Data
    public static class BudgetMaterialDto {
        @NotNull
        @Positive
        private Long materialId;

        @NotNull
        @Positive
        private Integer quantity;
    }
}
