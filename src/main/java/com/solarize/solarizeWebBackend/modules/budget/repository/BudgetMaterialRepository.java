package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.BudgetMaterial;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetMaterialRepository extends JpaRepository<BudgetMaterial, BudgetMaterialId> {
}
