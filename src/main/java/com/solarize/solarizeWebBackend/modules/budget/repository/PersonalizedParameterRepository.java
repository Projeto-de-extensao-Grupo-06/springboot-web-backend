package com.solarize.solarizeWebBackend.modules.budget.repository;

import com.solarize.solarizeWebBackend.modules.budget.model.PersonalizedParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalizedParameterRepository extends JpaRepository<PersonalizedParameter, Long> {
}
