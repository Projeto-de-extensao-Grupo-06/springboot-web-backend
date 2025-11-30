package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameterTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedParameterTemplateRepository extends JpaRepository<FixedParameterTemplate, Long> {
    Optional<FixedParameterTemplate> findByUniqueName(FixedParameterName uniqueName);

    FixedParameterTemplate getReferenceByUniqueName(FixedParameterName uniqueName);
}
