package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetParameterResponseDto {
    private Long id;
    private String name;
    private String description;
    private String metric;
    private Boolean isPreBudget;
    private Double fixedValue;
    private Boolean active;
    private LocalDateTime createdAt;
    private List<ParameterOptionResponseDto> options;
}
