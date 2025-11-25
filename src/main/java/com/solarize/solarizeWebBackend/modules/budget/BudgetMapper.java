package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.BudgetManualCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;

public class BudgetMapper {
    Budget toEntity(BudgetManualCreateDto dto) {
        if(dto == null) return null;

        Budget budget = new Budget();

        budget.setServiceCost(dto.getServiceCost());
        budget.setDiscount(dto.getDiscount());
        budget.setFinalBudget(budget.getFinalBudget());

        return budget;
    }


}
