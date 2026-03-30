package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.ParameterOption;
import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetParameterRepository;
import com.solarize.solarizeWebBackend.modules.budget.repository.ParameterOptionRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetParameterService {

    private final BudgetParameterRepository budgetParameterRepository;
    private final ParameterOptionRepository parameterOptionRepository;

    public Page<BudgetParameter> getAll(String search, Boolean isPreBudget, Pageable pageable) {
        return budgetParameterRepository.findAllActive(search, isPreBudget, pageable);
    }

    public BudgetParameter getById(Long id) {
        return budgetParameterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Budget parameter not found."));
    }

    public List<ParameterOption> getOptionsByParameterId(Long parameterId) {
        return parameterOptionRepository.findByBudgetParameterId(parameterId);
    }

    public BudgetParameter create(BudgetParameter parameter, List<ParameterOption> options) {
        BudgetParameter saved = budgetParameterRepository.save(parameter);
        options.stream().map(o -> {
            o.setBudgetParameter(saved);
            return parameterOptionRepository.save(o);
        }).toList();
        return saved;
    }

    public BudgetParameter update(Long id, BudgetParameter updated, List<ParameterOption> newOptions) {
        BudgetParameter existing = getById(id);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setMetric(updated.getMetric());
        existing.setIsPreBudget(updated.getIsPreBudget());
        existing.setFixedValue(updated.getFixedValue());

        BudgetParameter saved = budgetParameterRepository.save(existing);

        parameterOptionRepository.findByBudgetParameterId(id)
                .stream().map(o -> {
                    parameterOptionRepository.deleteById(o.getId());
                    return o;
                }).toList();

        newOptions.stream().map(o -> {
            o.setBudgetParameter(saved);
            return parameterOptionRepository.save(o);
        }).toList();

        return saved;
    }

    public void deactivate(Long id) {
        BudgetParameter parameter = getById(id);
        parameter.setActive(false);
        budgetParameterRepository.save(parameter);
    }
}
