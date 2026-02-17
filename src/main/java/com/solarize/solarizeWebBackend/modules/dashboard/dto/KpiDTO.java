
package com.solarize.solarizeWebBackend.modules.dashboard.dto;

import java.math.BigDecimal;

public record KpiDTO(
    BigDecimal totalProfitMargin,
    String mostCostlyChannel,
    Double projectCompletionRate,
    Double funnelConversionRate
) {}
