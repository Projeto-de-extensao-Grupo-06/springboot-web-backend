package com.solarize.solarizeWebBackend.modules.budget.repository;


import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetParameterRepository extends JpaRepository<BudgetParameter, Long> {

    @Query("""
        SELECT p FROM BudgetParameter p
        WHERE p.active = true
          AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:isPreBudget IS NULL OR p.isPreBudget = :isPreBudget)
    """)
    Page<BudgetParameter> findAllActive(
            @Param("search") String search,
            @Param("isPreBudget") Boolean isPreBudget,
            Pageable pageable
    );
}