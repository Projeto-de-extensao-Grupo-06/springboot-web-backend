package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BudgetParameterResponseDto {
    private Long id;
    private String name;
    private String description;
    private TaxType taxType;
    private Double additionTax;
}
