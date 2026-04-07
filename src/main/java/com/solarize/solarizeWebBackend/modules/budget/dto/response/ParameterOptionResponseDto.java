package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParameterOptionResponseDto {
    private Long id;
    private String type;
    private Double additionTax;
    private Double fixedCost;
}