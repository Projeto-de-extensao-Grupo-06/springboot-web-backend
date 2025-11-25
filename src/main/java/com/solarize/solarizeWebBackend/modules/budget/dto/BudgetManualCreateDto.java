package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BudgetManualCreateDto {
    @NotNull
    private List<BudgetMaterialDto> materialUrlIds;

    @NotNull
    @Valid
    private List<BudgetParameterCreateDto> materialParameters;

    @Positive
    private Double serviceCost;

    @Enumerated(EnumType.STRING)
    private TaxType discountType;
    private Double discount;


    private Boolean finalBudget;
}
