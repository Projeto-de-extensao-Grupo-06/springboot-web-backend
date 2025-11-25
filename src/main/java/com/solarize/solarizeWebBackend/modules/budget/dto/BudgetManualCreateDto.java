package com.solarize.solarizeWebBackend.modules.budget.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BudgetManualCreateDto {
    private List<Long> materialUrlIds;
    private List<BudgetParameterCreateDto> materialParameters;

    private Double serviceCost;
    private Boolean finalBudget;
}
