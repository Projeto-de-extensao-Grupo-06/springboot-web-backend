package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BudgetResponseDto {
    private Long id;
    private Double totalCost;
    private Double subtotal;
    private Double discount;
    private DiscountType discountType;
    private Boolean finalBudget;

    private List<BudgetMaterialResponseDto> materials;
    private List<FixedParameterResponseDto> fixedParameters;
    private List<PersonalizedParameterResponseDto> personalizedParameters;

    @Data
    @Builder
    public static class BudgetMaterialResponseDto {
        private Long materialUrlId;
        private String name;
        private String url;
        private Double unitPrice;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class PersonalizedParameterResponseDto {
        private Long id;
        private String name;
        private ParameterValueType type;
        private Double value;
    }

    @Data
    @Builder
    public static class FixedParameterResponseDto {
        private FixedParameterName parameterName;
        private ParameterValueType valueType;
        private Double value;
    }
}
