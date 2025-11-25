package com.solarize.solarizeWebBackend.modules.budget.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BudgetResponse {
    private Long id;
    private Double serviceCost;
    private Double materialCost;
    private Double subtotal;
    private Double discount;
    private Double totalCost;
    private Boolean finalBudget;

    private List<BudgetParameterResponseDto> parameters;

}
