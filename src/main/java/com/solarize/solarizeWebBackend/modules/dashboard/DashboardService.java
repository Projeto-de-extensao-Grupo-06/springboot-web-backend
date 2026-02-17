package com.solarize.solarizeWebBackend.modules.dashboard;

import com.solarize.solarizeWebBackend.modules.dashboard.dto.*;
import com.solarize.solarizeWebBackend.modules.dashboard.view.ViewAnalysisProjectFinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ViewAnalysisProjectFinanceRepository viewRepository;

    public KpiDTO getKpis(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        Object[] stats = viewRepository.getKpiRawStats(start, end);
        String mostCostlyChannel = viewRepository.findMostCostlyChannel(start, end);

        if (stats == null || stats.length == 0) {
            return new KpiDTO(java.math.BigDecimal.ZERO, mostCostlyChannel, 0.0, 0.0);
        }
        
        Object[] row = stats; 
        
        java.math.BigDecimal totalProfit = row[0] != null ? (java.math.BigDecimal) row[0] : java.math.BigDecimal.ZERO;
        Long totalProjects = (Long) row[1];
        Long completedProjects = row[2] != null ? ((Number) row[2]).longValue() : 0L;
        Long newProjects = row[3] != null ? ((Number) row[3]).longValue() : 0L;
        Long contractsSigned = row[4] != null ? ((Number) row[4]).longValue() : 0L;

        double completionRate = totalProjects > 0 ? (completedProjects * 100.0 / totalProjects) : 0.0;
        double funnelConversion = newProjects > 0 ? (contractsSigned * 100.0 / newProjects) : 0.0;

        return new KpiDTO(totalProfit, mostCostlyChannel, completionRate, funnelConversion);
    }

    public List<AcquisitionChannelDTO> getAcquisitionChannels(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        Long totalProjects = viewRepository.countByCreatedAtBetween(start, end);
        List<Object[]> counts = viewRepository.getChannelCounts(start, end);

        return counts.stream()
                .map(row -> {
                    String name = (String) row[0];
                    Long count = (Long) row[1];
                    Double percentage = totalProjects > 0 ? (count * 100.0 / totalProjects) : 0.0;
                    return new AcquisitionChannelDTO(name, count, percentage);
                })
                .toList();
    }

    public List<FinancialDTO> getFinancials(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.getFinancials(start, end);
    }

    public List<ProjectStatusDTO> getProjectStatus(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.getProjectStatus(start, end);
    }

    public List<SalesFunnelDTO> getSalesFunnel(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.getSalesFunnel(start, end);
    }
}
