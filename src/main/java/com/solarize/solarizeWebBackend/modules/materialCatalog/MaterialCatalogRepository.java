package com.solarize.solarizeWebBackend.modules.materialCatalog;

import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialCatalogRepository extends JpaRepository<MaterialCatalog, Long> {
    List<MaterialCatalog> findAll();
    Optional<MaterialCatalog> findById(Long id);
    List<MaterialCatalog> findByNameContainingIgnoreCase(String name);
}
