package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Boolean existsByProject(Project projectProxy);
}
