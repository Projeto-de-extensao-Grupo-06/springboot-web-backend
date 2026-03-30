package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetParameterFilterDto {
    private String search;
    private Boolean isPreBudget;
    private Integer page = 0;
    private Integer size = 30;
}