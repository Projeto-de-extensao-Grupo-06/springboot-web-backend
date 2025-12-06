package com.solarize.solarizeWebBackend.modules.material.repository;

import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialUrlRepository extends JpaRepository<MaterialUrl, Long> {
    List<MaterialUrl> findAllByIdIn(List<Long> ids);

    @Query("SELECT COUNT(m.id) FROM MaterialUrl m WHERE m.id IN :ids")
    Integer allExistsByIdIn(@Param("ids") List<Long> ids);
}
