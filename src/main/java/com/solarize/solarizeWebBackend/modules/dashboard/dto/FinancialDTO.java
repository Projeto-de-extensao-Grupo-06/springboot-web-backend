
package com.solarize.solarizeWebBackend.modules.dashboard.dto;

import java.math.BigDecimal;

public record FinancialDTO(
    Integer year,
    Integer month,
    BigDecimal totalCost,
    BigDecimal totalProfit
) {}
