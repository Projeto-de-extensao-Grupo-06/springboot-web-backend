package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.ConfigParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigParameterRepository extends JpaRepository<ConfigParameter, Long> {
    Optional<ConfigParameter> findByUniqueName(String uniqueName);
}
