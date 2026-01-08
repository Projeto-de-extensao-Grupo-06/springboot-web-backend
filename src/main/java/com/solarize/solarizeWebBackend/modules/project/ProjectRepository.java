package com.solarize.solarizeWebBackend.modules.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    @Query("""
        SELECT p FROM Project p
        WHERE p.isActive = true
          AND (:search IS NULL OR (
              LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(p.client.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(p.client.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(p.client.documentNumber) LIKE LOWER(CONCAT('%', :search, '%'))
          ))
          AND (:clientId IS NULL OR p.client.id = :clientId)
          AND (:responsibleId IS NULL OR p.responsible.id = :responsibleId)
          AND (
              (:statusList IS NOT NULL AND p.status IN :statusList)
              OR (:statusList IS NULL AND p.status NOT IN ('AWAITING_RETRY', 'NEGOTIATION_FAILED', 'COMPLETED'))
          )
        ORDER BY
          CASE WHEN EXISTS (
            SELECT 1 FROM Schedule s WHERE s.project = p AND s.isActive = true AND s.startDate >= CURRENT_TIMESTAMP
          ) THEN 0 ELSE 1 END,
          p.statusWeight ASC,
          p.createdAt DESC
    """)
    Page<Project> findAllProjects(
            @Param("search") String search,
            @Param("statusList") List<ProjectStatusEnum> statusList,
            @Param("responsibleId") Long responsibleId,
            @Param("clientId") Long clientId,
            Pageable pageable
    );


    Optional<Project> findByIdAndIsActiveTrue(Long projectId);
    List<Project> findByClientIdAndIsActiveTrue(Long clientId);
}

