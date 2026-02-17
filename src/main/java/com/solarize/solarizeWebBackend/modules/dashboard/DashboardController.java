package com.solarize.solarizeWebBackend.modules.dashboard;

import com.solarize.solarizeWebBackend.modules.dashboard.dto.*;
import com.solarize.solarizeWebBackend.modules.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final com.solarize.solarizeWebBackend.modules.dashboard.mapper.DashboardMapper dashboardMapper;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/kpis")
    public ResponseEntity<KpiDTO> getKpis(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        KpiStatsDTO stats = dashboardService.getKpiStats(startDate, endDate);
        String mostCostlyChannel = dashboardService.getMostCostlyChannel(startDate, endDate);
        
        return ResponseEntity.ok(dashboardMapper.toKpiDTO(stats, mostCostlyChannel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/acquisition-channels")
    public ResponseEntity<List<AcquisitionChannelDTO>> getAcquisitionChannels(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        List<Object[]> rawData = dashboardService.getAcquisitionChannels(startDate, endDate);
        List<AcquisitionChannelDTO> dtos = rawData.stream()
            .map(dashboardMapper::toAcquisitionChannelDTO)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/financials")
    public ResponseEntity<List<FinancialDTO>> getFinancials(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12); // Default to 12 months for trends
        if (endDate == null) endDate = LocalDate.now();

        List<Object[]> rawData = dashboardService.getFinancials(startDate, endDate);
        List<FinancialDTO> dtos = rawData.stream()
            .map(dashboardMapper::toFinancialDTO)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/project-status")
    public ResponseEntity<List<ProjectStatusDTO>> getProjectStatus(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        return ResponseEntity.ok(dashboardService.getProjectStatus(startDate, endDate));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/sales-funnel")
    public ResponseEntity<List<SalesFunnelDTO>> getSalesFunnel(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        return ResponseEntity.ok(dashboardService.getSalesFunnel(startDate, endDate));
    }
}
