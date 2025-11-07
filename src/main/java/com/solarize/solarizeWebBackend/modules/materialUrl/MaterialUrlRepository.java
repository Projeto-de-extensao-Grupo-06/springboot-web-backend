package com.solarize.solarizeWebBackend.modules.materialUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialUrlRepository extends JpaRepository<MaterialUrl, Long> {
    List<MaterialUrl> findByMaterial_Id(Long materialId);
}
