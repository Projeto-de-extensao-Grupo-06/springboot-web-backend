package com.solarize.solarizeWebBackend.modules.budget.dto;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BudgetParameterCreateDto {
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    private Double additionTax;
}
