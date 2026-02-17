
package com.solarize.solarizeWebBackend.modules.analysis.view;

import com.solarize.solarizeWebBackend.modules.analysis.dto.ProjectStatusDTO;
import com.solarize.solarizeWebBackend.modules.analysis.dto.KpiStatsDTO;
import com.solarize.solarizeWebBackend.modules.analysis.dto.SalesFunnelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewAnalysisProjectFinanceRepository extends JpaRepository<ViewAnalysisProjectFinance, Long> {

    @Query("""
        SELECT new com.solarize.solarizeWebBackend.modules.analysis.dto.KpiStatsDTO(
            COALESCE(SUM(v.profitMargin), 0),
            COUNT(v.idProject),
            SUM(CASE WHEN v.status = 'COMPLETED' THEN 1 ELSE 0 END),
            SUM(CASE WHEN v.status = 'NEW' THEN 1 ELSE 0 END),
            SUM(CASE WHEN v.status IN ('FINAL_BUDGET', 'INSTALLED', 'COMPLETED') THEN 1 ELSE 0 END),
            ROUND(COALESCE(SUM(CASE WHEN v.status = 'COMPLETED' THEN 1.0 ELSE 0.0 END) * 100.0 / NULLIF(COUNT(v.idProject), 0), 0), 2),
            ROUND(COALESCE(SUM(CASE WHEN v.status IN ('FINAL_BUDGET', 'SCHEDULED_INSTALLING_VISIT', 'INSTALLED', 'COMPLETED') THEN 1.0 ELSE 0.0 END) * 100.0 / NULLIF(COUNT(v.idProject), 0), 0), 2)
        )
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
    """)
    KpiStatsDTO getKpiRawStats(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT v.acquisitionChannel
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
        GROUP BY v.acquisitionChannel
        ORDER BY SUM(v.totalProjectCost) DESC
        LIMIT 1
    """)
    String findMostCostlyChannel(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT
            CASE v.acquisitionChannel
                WHEN 'SITE_BUDGET_FORM' THEN 'Site'
                WHEN 'INTERNAL_MANUAL_ENTRY' THEN 'Boca a Boca'
                WHEN 'WHATSAPP_BOT' THEN 'WhatsApp'
                ELSE CAST(v.acquisitionChannel AS string)
            END,
            COUNT(v),
            ROUND(COUNT(v) * 100.0 / NULLIF((SELECT COUNT(v2) FROM ViewAnalysisProjectFinance v2 WHERE v2.createdAt >= :startDate AND v2.createdAt <= :endDate), 0), 2)
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
        GROUP BY v.acquisitionChannel
        ORDER BY COUNT(v) DESC
    """)
    List<Object[]> getChannelCounts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
        SELECT
            YEAR(v.createdAt),
            MONTH(v.createdAt),
            SUM(v.totalProjectCost),
            SUM(v.profitMargin)
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
        GROUP BY YEAR(v.createdAt), MONTH(v.createdAt)
        ORDER BY YEAR(v.createdAt) ASC, MONTH(v.createdAt) ASC
    """)
    List<Object[]> getFinancials(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT new com.solarize.solarizeWebBackend.modules.analysis.dto.ProjectStatusDTO(
            CASE v.status
                WHEN 'COMPLETED' THEN 'Finalizado'
                WHEN 'NEGOTIATION_FAILED' THEN 'Negociação Falhada'
                WHEN 'SCHEDULED_TECHNICAL_VISIT' THEN 'Agendado'
                WHEN 'SCHEDULED_INSTALLING_VISIT' THEN 'Agendado'
                WHEN 'NEW' THEN 'Novo'
                ELSE 'Em andamento'
            END,
            COUNT(v)
        )
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
        GROUP BY
            CASE v.status
                WHEN 'COMPLETED' THEN 'Finalizado'
                WHEN 'NEGOTIATION_FAILED' THEN 'Negociação Falhada'
                WHEN 'SCHEDULED_TECHNICAL_VISIT' THEN 'Agendado'
                WHEN 'SCHEDULED_INSTALLING_VISIT' THEN 'Agendado'
                WHEN 'NEW' THEN 'Novo'
                ELSE 'Em andamento'
            END
    """)
    List<ProjectStatusDTO> getProjectStatus(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT new com.solarize.solarizeWebBackend.modules.analysis.dto.SalesFunnelDTO(
            v.funnelStage,
            COUNT(v)
        )
        FROM ViewAnalysisProjectFinance v
        WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate
          AND v.funnelStage IN ('Leads', 'Contrato Assinado', 'Instalado/Finalizado')
        GROUP BY v.funnelStage
        ORDER BY
            CASE v.funnelStage
                WHEN 'Leads' THEN 1
                WHEN 'Contrato Assinado' THEN 2
                WHEN 'Instalado/Finalizado' THEN 3
                ELSE 4
            END ASC
    """)
    List<SalesFunnelDTO> getSalesFunnel(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
