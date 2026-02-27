package com.solarize.solarizeWebBackend.modules.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiDTO {
    private Double totalProfitMargin;
    private String mostCostlyChannel;
    private Double projectCompletionRate;
    private Double funnelConversionRate;
}
