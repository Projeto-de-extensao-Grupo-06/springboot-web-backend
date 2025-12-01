package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ConfigValueType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import com.solarize.solarizeWebBackend.modules.budget.helper.BudgetCalcs;
import com.solarize.solarizeWebBackend.modules.budget.model.*;
import com.solarize.solarizeWebBackend.modules.budget.model.serializable.BudgetMaterialId;
import com.solarize.solarizeWebBackend.modules.budget.model.serializable.FixedParameterId;
import com.solarize.solarizeWebBackend.modules.budget.repository.*;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialUrlRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    private final ProjectRepository projectRepository;
    private final BudgetMaterialRepository budgetMaterialRepository;
    private final FixedParameterRepository fixedParameterRepository;
    private final PersonalizedParameterRepository personalizedParameterRepository;

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
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if(budgetRepository.existsByProject(project)) {
            throw new ConflictException("The project already has a linked budget.");
        }

        budget.getMaterials().forEach(m -> {
            MaterialUrl materialUrl = materialUrlRepository.getReferenceById(m.getMaterialUrl().getId());

           if(!materialUrlRepository.existsById(m.getMaterialUrl().getId())) {
               throw new NotFoundException("MaterialUrl with id " + m.getMaterialUrl().getId() + " does not exists");
           }

            m.setMaterialUrl(materialUrl);
            m.setPrice(materialUrl.getPrice());
            m.setBudget(budget);
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

        budget.getPersonalizedParameters().forEach(p -> p.setBudget(budget));

        Map<String, Double> budgetCost = BudgetCalcs.budgetTotalCost(budget);


        budget.setProject(project);
        budget.setSubtotal(budgetCost.get("subtotal"));
        budget.setTotalCost(budgetCost.get("totalCost"));

        return budgetRepository.save(budget);
    }

    public Budget getBudget(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        return budgetRepository.findByProject(project)
                .orElseThrow(() -> new NotFoundException("The project does not have a linked budget."));
    }

    public Budget updateBudget(Budget budget, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));


        Budget currentBudget = budgetRepository.findByProject(project)
                .orElseThrow(() -> new ConflictException("The project don't have a linked budget."));


        currentBudget.setDiscount(
                Optional.ofNullable(budget.getDiscount())
                        .orElse(currentBudget.getDiscount())
        );

        currentBudget.setDiscountType(
                Optional.ofNullable(budget.getDiscountType())
                        .orElse(currentBudget.getDiscountType())
        );

        currentBudget.setFinalBudget(
                Optional.ofNullable(budget.getFinalBudget())
                        .orElse(currentBudget.getFinalBudget())
        );

        Map<String, Double> budgetCosts = BudgetCalcs.budgetTotalCost(currentBudget);
        currentBudget.setTotalCost(budgetCosts.get("totalCost"));
        currentBudget.setSubtotal(budgetCosts.get("subtotal"));

        return budgetRepository.save(currentBudget);
    }

    public Budget updateMaterial(List<BudgetMaterial> budgetMaterials, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));


        Budget budget = budgetRepository.findByProject(project)
                .orElseThrow(() -> new ConflictException("The project don't have a linked budget."));


        budgetMaterials.forEach(budgetMaterial -> {
            MaterialUrl materialUrl = materialUrlRepository.findById(budgetMaterial.getMaterialUrl().getId())
                    .orElseThrow(() -> new NotFoundException("Material url does not exists."));


            Optional<BudgetMaterial> budgetMaterialExists = budgetMaterialRepository.findById(new BudgetMaterialId(budget.getId(), materialUrl.getId()));

            if(budgetMaterialExists.isPresent()) {
                budgetMaterialExists.get().setQuantity(budgetMaterial.getQuantity());
                int index = budget.getMaterials().indexOf(budgetMaterialExists.get());

                budget.getMaterials().set(index, budgetMaterialExists.get());
            } else {
                budgetMaterial.setBudget(budget);
                budgetMaterial.setPrice(materialUrl.getPrice());

                if(!materialUrlRepository.existsById(budgetMaterial.getMaterialUrl().getId())) {
                    throw new NotFoundException("Material does not exists");
                }

                budget.getMaterials().add(budgetMaterial);
            }
        });

        Map<String, Double> budgetCosts = BudgetCalcs.budgetTotalCost(budget);
        budget.setSubtotal(budgetCosts.get("subtotal"));
        budget.setTotalCost(budgetCosts.get("totalCost"));

        return budgetRepository.save(budget);
    }

    public Budget updateFixedParameter(Long projectId, List<FixedParameter> fixedParameters) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));


        Budget budget = budgetRepository.findByProject(project)
                .orElseThrow(() -> new ConflictException("The project don't have a linked budget."));

        fixedParameters.forEach(p -> {
            Optional<FixedParameterTemplate> template = fixedParameterTemplateRepository
                    .findByUniqueName(p.getTemplate().getUniqueName());

            if(template.isEmpty()) {
                throw new NotFoundException("Fixed parameter does not exists.");
            }

            Optional<FixedParameter> savedFixedParameter = fixedParameterRepository
                    .findById(new FixedParameterId(template.get().getId(), budget.getId()));

            if(savedFixedParameter.isPresent()) {
                savedFixedParameter.get().setParameterValue(p.getParameterValue());

                int index = budget.getFixedParameters().indexOf(savedFixedParameter.get());
                budget.getFixedParameters().set(index, savedFixedParameter.get());
            } else {
                p.setBudget(budget);
                p.setTemplate(template.get());

                budget.getFixedParameters().add(p);
            }
        });

        Map<String, Double> budgetCosts = BudgetCalcs.budgetTotalCost(budget);
        budget.setTotalCost(budgetCosts.get("totalCost"));
        budget.setSubtotal(budgetCosts.get("subtotal"));

        return budgetRepository.save(budget);
    }

    public Budget updatePersonalizedParameter(Long projectId, List<PersonalizedParameter> fixedParameters) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));


        Budget budget = budgetRepository.findByProject(project)
                .orElseThrow(() -> new ConflictException("The project don't have a linked budget."));

        fixedParameters.forEach(p -> {
            Optional<PersonalizedParameter> savedPersonalizedParameter = Optional.empty();

            if(p.getId() != null) {
                savedPersonalizedParameter = personalizedParameterRepository.findById(p.getId());
            }

            if(savedPersonalizedParameter.isPresent()) {
                savedPersonalizedParameter.get().setParameterValue(p.getParameterValue());
                savedPersonalizedParameter.get().setType(p.getType());
                savedPersonalizedParameter.get().setName(p.getName());

                int index = budget.getPersonalizedParameters().indexOf(savedPersonalizedParameter.get());
                budget.getPersonalizedParameters().set(index, savedPersonalizedParameter.get());
            } else {
                p.setId(null);
                p.setBudget(budget);

                budget.getPersonalizedParameters().add(p);
            }
        });

        Map<String, Double> budgetCosts = BudgetCalcs.budgetTotalCost(budget);
        budget.setTotalCost(budgetCosts.get("totalCost"));
        budget.setSubtotal(budgetCosts.get("subtotal"));

        return budgetRepository.save(budget);
    }
}
