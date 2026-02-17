package com.solarize.solarizeWebBackend.modules.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByDocumentNumber(String documentNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByDocumentNumberAndIdNot(String documentNumber, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    @Query("""
        SELECT c FROM Client c
        LEFT JOIN c.mainAddress a
        WHERE (:status IS NULL OR c.status = :status)
          AND ((:city) IS NULL OR a.city IN (:city))
          AND ((:state) IS NULL OR a.state IN (:state))
          AND (cast(:startDate as timestamp) IS NULL OR c.createdAt >= :startDate)
          AND (cast(:endDate as timestamp) IS NULL OR c.createdAt <= :endDate)
          AND (:search IS NULL OR :search = '' OR (
              LOWER(CONCAT(c.firstName, ' ', c.lastName)) LIKE LOWER(CONCAT('%', :search, '%')) OR
              c.email LIKE CONCAT('%', :search, '%') OR
              c.phone LIKE CONCAT('%', :search, '%') OR
              c.documentNumber LIKE CONCAT('%', :search, '%')
          ))
    """)
    Page<Client> findAllClients(
            @Param("search") String search,
            @Param("status") ClientStatusEnum status,
            @Param("city") List<String> city,
            @Param("state") List<String> state,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
