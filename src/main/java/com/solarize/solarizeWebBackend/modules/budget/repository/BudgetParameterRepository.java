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
    WHERE (
        :search IS NULL OR
        CAST(FUNCTION('UNACCENT', LOWER(p.name)) AS string)
            LIKE CONCAT(
                '%',
                CAST(FUNCTION('UNACCENT', LOWER(:search)) AS string),
                '%'
            )
    )
    AND (:isPreBudget IS NULL OR p.isPreBudget = :isPreBudget)
    AND (:active IS NULL OR p.active = :active)
""")
    Page<BudgetParameter> findAllActive(
            @Param("search") String search,
            @Param("isPreBudget") Boolean isPreBudget,
            @Param("active") Boolean active,
            Pageable pageable
    );
}