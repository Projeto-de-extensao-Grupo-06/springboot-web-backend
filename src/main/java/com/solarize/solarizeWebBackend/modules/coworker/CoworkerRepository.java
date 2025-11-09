package com.solarize.solarizeWebBackend.modules.coworker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    Optional<Coworker> findByEmailAndIsActiveTrue(String email);
    Optional<Coworker> findByPhoneAndIsActiveTrue(String phone);

    List<Coworker> findAllByisActiveTrue();
    Optional<Coworker> findByIdAndIsActiveTrue(Long id);
    boolean existsByEmailAndIsActiveTrue(String email);
    boolean existsByPhoneAndIsActiveTrue(String Phone);
    boolean existsByIsActive(Boolean isActive);

    @Query("SELECT c FROM Coworker c JOIN FETCH c.permission WHERE c.email = :email")
    Optional<Coworker> findByEmailWithPermissions(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
