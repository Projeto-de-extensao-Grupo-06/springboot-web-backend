package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameterTemplate;
import com.solarize.solarizeWebBackend.modules.budget.model.serializable.FixedParameterId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedParameterRepository extends JpaRepository<FixedParameter, FixedParameterId> {
    Optional<FixedParameter> findFixedParameterByTemplateAndBudget(FixedParameterTemplate template, Budget budget);
}
