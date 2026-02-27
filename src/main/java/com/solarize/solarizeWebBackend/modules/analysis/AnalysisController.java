package com.solarize.solarizeWebBackend.modules.analysis;

import com.solarize.solarizeWebBackend.modules.analysis.dto.*;
import com.solarize.solarizeWebBackend.modules.analysis.mapper.AnalysisMapper;
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
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AnalysisMapper analysisMapper;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/kpis")
    public ResponseEntity<KpiDTO> getKpis(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        KpiStatsDTO stats = analysisService.getKpiStats(startDate, endDate);
        String mostCostlyChannel = analysisService.getMostCostlyChannel(startDate, endDate);
        
        return ResponseEntity.ok(analysisMapper.toKpiDTO(stats, mostCostlyChannel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/acquisition-channels")
    public ResponseEntity<List<AcquisitionChannelDTO>> getAcquisitionChannels(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        List<Object[]> rawData = analysisService.getAcquisitionChannels(startDate, endDate);
        List<AcquisitionChannelDTO> dtos = rawData.stream()
            .map(analysisMapper::toAcquisitionChannelDTO)
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

        List<Object[]> rawData = analysisService.getFinancials(startDate, endDate);
        List<FinancialDTO> dtos = rawData.stream()
            .map(analysisMapper::toFinancialDTO)
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

        return ResponseEntity.ok(analysisService.getProjectStatus(startDate, endDate));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/sales-funnel")
    public ResponseEntity<List<SalesFunnelDTO>> getSalesFunnel(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) startDate = LocalDate.now().minusMonths(12);
        if (endDate == null) endDate = LocalDate.now();

        return ResponseEntity.ok(analysisService.getSalesFunnel(startDate, endDate));
    }
}
