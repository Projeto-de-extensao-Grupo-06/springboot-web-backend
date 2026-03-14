package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreBudgetEstimationDto {
    private Double kwp;
    private Double bill;
    private Double cost;
    private Double paybackYears;
    private String message;
}
