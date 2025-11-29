package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.BudgetManualCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ConfigValueType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import com.solarize.solarizeWebBackend.modules.budget.model.*;
import com.solarize.solarizeWebBackend.modules.budget.repository.*;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ConfigParameterRepository configParameterRepository;
    private final FixedParameterTemplateRepository fixedParameterTemplateRepository;
    private final MaterialUrlRepository materialUrlRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void creatingConfigParams() {
        List<ConfigParameter> parameters = List.of(
                ConfigParameter
                        .builder()
                        .uniqueName("KWH_VALUE")
                        .configValueType(ConfigValueType.AMOUNT)
                        .parameterValue(0.64)
                        .build(),
                ConfigParameter
                        .builder()
                        .uniqueName("FUEL_VALUE")
                        .configValueType(ConfigValueType.AMOUNT)
                        .parameterValue(6.16)
                        .build(),
                ConfigParameter
                        .builder()
                        .uniqueName("KM_PER_LITER")
                        .configValueType(ConfigValueType.KM)
                        .parameterValue(8.0)
                        .build(),
                ConfigParameter
                        .builder()
                        .uniqueName("VALUE_MULTIPLIER_PER_KWH")
                        .configValueType(ConfigValueType.AMOUNT)
                        .parameterValue(100.0)
                        .build()
        );


        for(ConfigParameter parameter : parameters) {
           Optional<ConfigParameter> configParam =  configParameterRepository.findByUniqueName(parameter.getUniqueName());

           if(configParam.isEmpty()) {
               configParameterRepository.save(parameter);
           }
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    public void creatingFixedParametersTemplates() {
        List<FixedParameterTemplate> templates = List.of(
                FixedParameterTemplate
                  .builder()
                  .uniqueName(FixedParameterName.FOOD)
                  .type(ParameterValueType.AMOUNT)
                  .build(),

                FixedParameterTemplate
                  .builder()
                  .uniqueName(FixedParameterName.TRANSPORT)
                  .type(ParameterValueType.AMOUNT)
                  .build(),

                FixedParameterTemplate
                        .builder()
                        .uniqueName(FixedParameterName.LABOR) // Mão de obra
                        .type(ParameterValueType.AMOUNT)
                        .build(),

                FixedParameterTemplate
                        .builder()
                        .uniqueName(FixedParameterName.RISK_FACTOR)
                        .type(ParameterValueType.AMOUNT)
                        .build(),

                FixedParameterTemplate
                        .builder()
                        .uniqueName(FixedParameterName.ENGINEERING_FEE) // Custo com o engenheiro.
                        .type(ParameterValueType.AMOUNT)
                        .build(),

                FixedParameterTemplate
                        .builder()
                        .uniqueName(FixedParameterName.APPROVAL_FEE) // Homologação do projeto.
                        .type(ParameterValueType.AMOUNT)
                        .build()
        );

        for(FixedParameterTemplate parameter : templates) {
            Optional<FixedParameterTemplate> configParam =  fixedParameterTemplateRepository.findByUniqueName(parameter.getUniqueName());

            if(configParam.isEmpty()) {
                fixedParameterTemplateRepository.save(parameter);
            }
        }
    }


    public Budget manualBudgetCreating
            (
                    Budget budget,
                    Long projectId
            )
    {
//        new BudgetManualCreateDto()

        return budget;
    }
}
