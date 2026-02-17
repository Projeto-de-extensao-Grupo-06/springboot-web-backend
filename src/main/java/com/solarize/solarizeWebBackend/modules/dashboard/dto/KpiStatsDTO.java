package com.solarize.solarizeWebBackend.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiStatsDTO {
    private Double totalProfit;
    private Long totalProjects;
    private Long completedProjects;
    private Long newProjects;
    private Long contractsSignedProjects;
    private Double completionRate;
    private Double funnelConversionRate;
}
