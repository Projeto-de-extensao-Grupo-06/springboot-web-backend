package com.solarize.solarize_web_backend.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    Optional<Coworker> findByEmail(String email);

    @Query("SELECT c FROM Coworker c JOIN FETCH c.permission WHERE c.email = :email")
    Optional<Coworker> findByEmailWithPermissions(String email);
}
