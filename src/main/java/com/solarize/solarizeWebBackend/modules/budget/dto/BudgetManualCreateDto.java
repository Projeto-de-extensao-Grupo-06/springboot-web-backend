package com.solarize.solarizeWebBackend.modules.budget.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BudgetManualCreateDto {
    private List<Material>

    private Double serviceCost;
    private Boolean finalBudget = false;
}
