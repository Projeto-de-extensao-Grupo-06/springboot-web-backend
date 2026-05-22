package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutomaticBudgetConfigDto {

    @NotNull
    @DecimalMin(value = "0.0", message = "Price per kWp cannot be negative.")
    private Double pricePerKwp;

    @NotNull
    @DecimalMin(value = "0.0", message = "Energy tariff cannot be negative.")
    private Double energyTariff;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double propertyTypeCasaTerrea;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double propertyTypeSobrado;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double propertyTypePredio;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double roofTypeMetalico;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double roofTypeCeramico;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double roofTypeFibrocimento;

    @NotNull
    @DecimalMin(value = "0.0", message = "Value cannot be negative.")
    private Double roofTypeLaje;
}
