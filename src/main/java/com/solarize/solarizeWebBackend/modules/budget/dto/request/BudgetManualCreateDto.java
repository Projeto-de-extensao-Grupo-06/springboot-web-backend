package com.solarize.solarizeWebBackend.modules.budget.dto.request;

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
    @PositiveOrZero
    private Double discount = 0.0;
    private DiscountType discountType = DiscountType.PERCENT;
    private Boolean finalBudget = false;

    @Valid
    private List<BudgetMaterialCreateDto> materials = new ArrayList<>();

    @Valid
    private List<FixedParameterCreateDto> fixedParameters = new ArrayList<>();

    @Valid
    private List<PersonalizedParameterDto> personalizedParameters = new ArrayList<>();

    @Data
    public static class BudgetMaterialCreateDto {
        @NotNull
        @Positive
        private Long materialId;

        @NotNull
        @Positive
        private Integer quantity;
    }

    @Data
    public static class PersonalizedParameterDto {
        @NotBlank
        private String name;

        @NotNull
        private ParameterValueType type;

        @PositiveOrZero
        private Double value;
    }

    @Data
    public static class FixedParameterCreateDto {
        @NotNull
        private FixedParameterName parameterName;

        @PositiveOrZero
        @NotNull
        private Double value;
    }
}
