
package com.solarize.solarizeWebBackend.modules.dashboard.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "VIEW_ANALYSIS_PROJECT_FINANCE")
@Getter
public class ViewAnalysisProjectFinance {
    @Id
    private Long idProject;
    private String acquisitionChannel;
    private LocalDateTime createdAt;
    private String status;
    private BigDecimal totalRevenue;
    private BigDecimal totalProjectCost;
    private BigDecimal profitMargin;
}
