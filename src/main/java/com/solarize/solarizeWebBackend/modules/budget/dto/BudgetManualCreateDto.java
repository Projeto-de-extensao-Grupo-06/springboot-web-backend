package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class BudgetManualCreateDto {
    @Positive
    private Double discount = 0.0;
    private DiscountType discountType = DiscountType.PERCENT;
    private Boolean finalBudget = false;

    private List<BudgetMaterialCreateDto> materials = new ArrayList<>();
    private List<FixedParameterCreateDto> fixedParameters = new ArrayList<>();
    private List<PersonalizedParameterDto> personalizedParameters = new ArrayList<>();

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

    @Data
    public static class PersonalizedParameterDto {
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

    @Data
    public static class FixedParameterCreateDto {
        @Valid
        @NotNull
        private FixedParameterName parameterName;

        @Valid
        @PositiveOrZero
        @NotNull
        private Double value;
    }
}
