package com.solarize.solarizeWebBackend.modules.coworker;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    Optional<Coworker> findByEmail(String email);
    Optional<Coworker> findByPhone(String phone);

    @Query("SELECT c FROM Coworker c JOIN FETCH c.permission WHERE c.email = :email")
    Optional<Coworker> findByEmailWithPermissions(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
