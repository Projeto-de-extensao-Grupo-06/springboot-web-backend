package com.solarize.solarizeWebBackend.modules.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
        WHERE (:search IS NULL OR (
            LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(c.documentNumber) LIKE LOWER(CONCAT('%', :search, '%'))
        ))
        AND (:status IS NULL OR c.status = :status)
    """)
    Page<Client> findAllClients(
            @Param("search") String search,
            @Param("status") ClientStatusEnum status,
            Pageable pageable
    );
}
