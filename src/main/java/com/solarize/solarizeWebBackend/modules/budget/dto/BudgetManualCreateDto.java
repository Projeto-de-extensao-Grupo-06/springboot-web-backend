package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BudgetManualCreateDto {
    @Positive
    private Double discount = 0.0;

    private DiscountType discountType = DiscountType.PERCENT;

    private List<BudgetMaterialCreateDto> material = new ArrayList<>();
    private List<FixedParameterCreateDto> fixedParameters = new ArrayList<>();
    private List<PersonalizedParameterDto> personalizedParameters = new ArrayList<>();
}
