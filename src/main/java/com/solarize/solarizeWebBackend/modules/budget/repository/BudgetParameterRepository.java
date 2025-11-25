package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetParameterRepository extends JpaRepository<BudgetParameter, Long> {
}
