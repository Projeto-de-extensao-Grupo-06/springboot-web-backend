package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BudgetPatchDto {
    @PositiveOrZero
    private Double discount = 0.0;
    private DiscountType discountType = DiscountType.PERCENT;
    private Boolean finalBudget = false;
}
