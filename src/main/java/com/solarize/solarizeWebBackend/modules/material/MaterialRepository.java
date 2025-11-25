package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.material.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
