package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ConfigValueType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import com.solarize.solarizeWebBackend.modules.budget.helper.BudgetCalcs;
import com.solarize.solarizeWebBackend.modules.budget.model.*;
import com.solarize.solarizeWebBackend.modules.budget.repository.*;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialUrlRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ConfigParameterRepository configParameterRepository;
    private final FixedParameterTemplateRepository fixedParameterTemplateRepository;
    private final MaterialUrlRepository materialUrlRepository;

    @PersistenceContext
    private final EntityManager em;

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
        Project projectProxy = em.getReference(Project.class, projectId);

        if(budgetRepository.existsByProject(projectProxy)) {
            throw new ConflictException("The project already has a linked budget.");
        }

        budget.getMaterials().forEach(m -> {
            MaterialUrl materialUrl = materialUrlRepository.getReferenceById(m.getMaterialUrl().getId());

            try {
                m.setMaterialUrl(materialUrl);
                m.setPrice(materialUrl.getPrice());
                m.setBudget(budget);
            } catch (EntityNotFoundException e) {
                log.error("Material id: {} does not exists in database.", m.getMaterialUrl().getId());
                throw new BadRequestException(String.format("Material id: %d does not exists in database.", m.getMaterialUrl().getId()));
            }
        });

        budget.getFixedParameters().forEach(p -> {
            FixedParameterTemplate template = fixedParameterTemplateRepository.getReferenceByUniqueName(p.getTemplate().getUniqueName());

            try {
                p.setTemplate(template);
                p.getTemplate().setType(template.getType());
                p.setBudget(budget);
            } catch (EntityNotFoundException e) {
                log.error("Template {} does not exists in database.", p.getTemplate().getUniqueName());
                throw new BadRequestException(String.format("template %s does not exists in database.", p.getTemplate().getUniqueName()));
            }
        });

        Map<String, Double> budgetCost = BudgetCalcs.budgetTotalCost(budget);


        budget.setProject(projectProxy);
        budget.setSubtotal(budgetCost.get("subtotal"));
        budget.setTotalCost(budgetCost.get("totalCost"));


        log.info(String.valueOf(budget.getSubtotal()));
        log.info(String.valueOf(budget.getTotalCost()));


        return budgetRepository.save(budget);
    }
}
