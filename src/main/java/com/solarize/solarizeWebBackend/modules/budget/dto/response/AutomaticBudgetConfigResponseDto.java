package com.solarize.solarizeWebBackend.modules.budget.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutomaticBudgetConfigResponseDto {
    private Double pricePerKwp;
    private Double energyTariff;
    private Double propertyTypeCasaTerrea;
    private Double propertyTypeSobrado;
    private Double propertyTypePredio;
    private Double roofTypeMetalico;
    private Double roofTypeCeramico;
    private Double roofTypeFibrocimento;
    private Double roofTypeLaje;
}
