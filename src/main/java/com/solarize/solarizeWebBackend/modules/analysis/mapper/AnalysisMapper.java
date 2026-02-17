package com.solarize.solarizeWebBackend.modules.analysis.mapper;

import com.solarize.solarizeWebBackend.modules.analysis.dto.AcquisitionChannelDTO;
import com.solarize.solarizeWebBackend.modules.analysis.dto.FinancialDTO;
import com.solarize.solarizeWebBackend.modules.analysis.dto.KpiDTO;
import com.solarize.solarizeWebBackend.modules.analysis.dto.KpiStatsDTO;
import org.springframework.stereotype.Component;

@Component
public class AnalysisMapper {

    public KpiDTO toKpiDTO(KpiStatsDTO stats, String mostCostlyChannel) {
        if (stats == null) {
            return KpiDTO.builder()
                    .totalProfitMargin(0.0)
                    .mostCostlyChannel(mostCostlyChannel)
                    .projectCompletionRate(0.0)
                    .funnelConversionRate(0.0)
                    .build();
        }

        return KpiDTO.builder()
                .totalProfitMargin(stats.getTotalProfit() != null ? stats.getTotalProfit() : 0.0)
                .mostCostlyChannel(mostCostlyChannel)
                .projectCompletionRate(stats.getCompletionRate() != null ? stats.getCompletionRate() : 0.0)
                .funnelConversionRate(stats.getFunnelConversionRate() != null ? stats.getFunnelConversionRate() : 0.0)
                .build();
    }

    public AcquisitionChannelDTO toAcquisitionChannelDTO(Object[] row) {
        String name = (String) row[0];
        Long count = (Long) row[1];
        Double percentage = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
        
        return AcquisitionChannelDTO.builder()
                .name(name)
                .projectCount(count)
                .percentage(percentage)
                .build();
    }

    public FinancialDTO toFinancialDTO(Object[] row) {
        return FinancialDTO.builder()
                .year((Integer) row[0])
                .month((Integer) row[1])
                .totalCost(row[2] != null ? ((Number) row[2]).doubleValue() : 0.0)
                .totalProfit(row[3] != null ? ((Number) row[3]).doubleValue() : 0.0)
                .build();
    }
}
