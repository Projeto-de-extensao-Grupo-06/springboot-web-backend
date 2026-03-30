package com.solarize.solarizeWebBackend.modules.budget.repository;


import com.solarize.solarizeWebBackend.modules.budget.model.ParameterOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterOptionRepository extends JpaRepository<ParameterOption, Long> {
    List<ParameterOption> findByBudgetParameterId(Long budgetParameterId);
}