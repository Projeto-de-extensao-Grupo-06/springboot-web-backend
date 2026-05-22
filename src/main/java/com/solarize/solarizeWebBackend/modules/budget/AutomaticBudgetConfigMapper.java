package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.AutomaticBudgetConfigDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.AutomaticBudgetConfigResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.AutomaticBudgetConfigKey;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ConfigValueType;
import com.solarize.solarizeWebBackend.modules.budget.model.ConfigParameter;

import java.util.List;

public class AutomaticBudgetConfigMapper {

    public static AutomaticBudgetConfigResponseDto toDto(
            Double pricePerKwp,
            Double energyTariff,
            Double propertyTypeCasaTerrea,
            Double propertyTypeSobrado,
            Double propertyTypePredio,
            Double roofTypeMetalico,
            Double roofTypeCeramico,
            Double roofTypeFibrocimento,
            Double roofTypeLaje
    ) {
        return AutomaticBudgetConfigResponseDto.builder()
                .pricePerKwp(pricePerKwp)
                .energyTariff(energyTariff)
                .propertyTypeCasaTerrea(propertyTypeCasaTerrea)
                .propertyTypeSobrado(propertyTypeSobrado)
                .propertyTypePredio(propertyTypePredio)
                .roofTypeMetalico(roofTypeMetalico)
                .roofTypeCeramico(roofTypeCeramico)
                .roofTypeFibrocimento(roofTypeFibrocimento)
                .roofTypeLaje(roofTypeLaje)
                .build();
    }

    public static List<ConfigParameter> toEntityList(AutomaticBudgetConfigDto dto) {
        return List.of(
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.SOLAR_PRICE_PER_KWP.name())
                        .parameterValue(dto.getPricePerKwp())
                        .configValueType(ConfigValueType.AMOUNT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.SOLAR_TARIFF.name())
                        .parameterValue(dto.getEnergyTariff())
                        .configValueType(ConfigValueType.AMOUNT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.PROPERTY_TYPE_CASA_TERREA.name())
                        .parameterValue(dto.getPropertyTypeCasaTerrea())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.PROPERTY_TYPE_SOBRADO.name())
                        .parameterValue(dto.getPropertyTypeSobrado())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.PROPERTY_TYPE_PREDIO.name())
                        .parameterValue(dto.getPropertyTypePredio())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.ROOF_TYPE_METALICO.name())
                        .parameterValue(dto.getRoofTypeMetalico())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.ROOF_TYPE_CERAMICO.name())
                        .parameterValue(dto.getRoofTypeCeramico())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.ROOF_TYPE_FIBROCIMENTO.name())
                        .parameterValue(dto.getRoofTypeFibrocimento())
                        .configValueType(ConfigValueType.PERCENT)
                        .build(),
                ConfigParameter.builder()
                        .uniqueName(AutomaticBudgetConfigKey.ROOF_TYPE_LAJE.name())
                        .parameterValue(dto.getRoofTypeLaje())
                        .configValueType(ConfigValueType.PERCENT)
                        .build()
        );
    }
}