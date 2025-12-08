package com.solarize.solarizeWebBackend.modules.material.repository;

import com.solarize.solarizeWebBackend.modules.material.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findAllByHiddenFalse();
    Optional<Material> findByIdAndHiddenFalse(Long id);
    boolean existsByName(String name);

}
