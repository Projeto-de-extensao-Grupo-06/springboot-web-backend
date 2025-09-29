package com.solarize.solarize_web_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solarize.sptech.solarize.jwt.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
