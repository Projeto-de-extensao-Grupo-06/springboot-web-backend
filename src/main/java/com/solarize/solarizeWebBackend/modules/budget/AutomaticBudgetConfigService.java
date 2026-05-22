package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.response.AutomaticBudgetConfigResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.AutomaticBudgetConfigKey;
import com.solarize.solarizeWebBackend.modules.budget.model.ConfigParameter;
import com.solarize.solarizeWebBackend.modules.budget.repository.ConfigParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomaticBudgetConfigService {

    private final ConfigParameterRepository configParameterRepository;

    public AutomaticBudgetConfigResponseDto getConfig() {
        return AutomaticBudgetConfigMapper.toDto(
                getValue(AutomaticBudgetConfigKey.SOLAR_PRICE_PER_KWP, 4500.0),
                getValue(AutomaticBudgetConfigKey.SOLAR_TARIFF, 0.90),
                getValue(AutomaticBudgetConfigKey.PROPERTY_TYPE_CASA_TERREA, 0.0),
                getValue(AutomaticBudgetConfigKey.PROPERTY_TYPE_SOBRADO, 0.0),
                getValue(AutomaticBudgetConfigKey.PROPERTY_TYPE_PREDIO, 0.0),
                getValue(AutomaticBudgetConfigKey.ROOF_TYPE_METALICO, 0.0),
                getValue(AutomaticBudgetConfigKey.ROOF_TYPE_CERAMICO, 0.0),
                getValue(AutomaticBudgetConfigKey.ROOF_TYPE_FIBROCIMENTO, 0.0),
                getValue(AutomaticBudgetConfigKey.ROOF_TYPE_LAJE, 0.0)
        );
    }

    public AutomaticBudgetConfigResponseDto saveConfig(List<ConfigParameter> toSave) {
        toSave.stream().map(param -> {
            ConfigParameter existing = configParameterRepository
                    .findByUniqueName(param.getUniqueName())
                    .orElse(ConfigParameter.builder()
                            .uniqueName(param.getUniqueName())
                            .configValueType(param.getConfigValueType())
                            .build());

            existing.setParameterValue(param.getParameterValue());
            return configParameterRepository.save(existing);
        }).toList();

        return getConfig();
    }

    private Double getValue(AutomaticBudgetConfigKey key, Double defaultValue) {
        return configParameterRepository
                .findByUniqueName(key.name())
                .map(ConfigParameter::getParameterValue)
                .orElse(defaultValue);
    }
}