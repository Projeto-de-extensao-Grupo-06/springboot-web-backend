package com.solarize.solarizeWebBackend.materialCatalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCatalogRepository extends JpaRepository<MaterialCatalog, Integer> {
}
