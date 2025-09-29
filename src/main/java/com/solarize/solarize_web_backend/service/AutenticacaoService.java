package com.solarize.solarize_web_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import solarize.sptech.solarize.jwt.dto.UsuarioDetalhesDto;
import solarize.sptech.solarize.jwt.entity.Usuario;
import solarize.sptech.solarize.jwt.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()){

            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));

        }

        return new UsuarioDetalhesDto(usuarioOpt.get());

    }

}
