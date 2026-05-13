package com.solarize.solarizeWebBackend.modules.coworker;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    Optional<Coworker> findByEmailAndIsActiveTrue(String email);

    @Query("SELECT c FROM Coworker c JOIN FETCH c.permission WHERE c.email = :email")
    Optional<Coworker> findByEmailWithPermissions(String email);

    Optional<Coworker> findByIdAndIsActiveTrue(long id);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Coworker> findByPhoneAndIsActiveTrue(String phone);

    List<Coworker> findAllByisActiveTrue();

    @Query("""
        SELECT c FROM Coworker c
        WHERE c.isActive = true
          AND (:search IS NULL OR :search = '' OR (
             CAST(FUNCTION('UNACCENT', LOWER(CONCAT(c.firstName, ' ', c.lastName))) AS string)
             LIKE CONCAT('%', CAST(FUNCTION('UNACCENT', LOWER(:search)) AS string), '%') OR
              c.email LIKE CONCAT('%', :search, '%') OR
              c.phone LIKE CONCAT('%', :search, '%')
          ))
    """)
    List<Coworker> searchCoworkers(@Param("search") String search);
}
