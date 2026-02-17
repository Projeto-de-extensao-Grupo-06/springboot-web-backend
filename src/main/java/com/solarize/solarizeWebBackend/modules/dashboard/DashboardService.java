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

    public KpiStatsDTO getKpiStats(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.getKpiRawStats(start, end);
    }

    public String getMostCostlyChannel(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.findMostCostlyChannel(start, end);
    }

    public List<Object[]> getAcquisitionChannels(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return viewRepository.getChannelCounts(start, end);
    }

    public List<Object[]> getFinancials(LocalDate startDate, LocalDate endDate) {
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
