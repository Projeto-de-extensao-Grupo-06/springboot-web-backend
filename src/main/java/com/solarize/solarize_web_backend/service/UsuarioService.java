package com.solarize.solarize_web_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import solarize.sptech.solarize.jwt.config.GerenciadorTokenJwt;
import solarize.sptech.solarize.jwt.dto.UsuarioListarDto;
import solarize.sptech.solarize.jwt.dto.UsuarioMapper;
import solarize.sptech.solarize.jwt.dto.UsuarioTokenDto;
import solarize.sptech.solarize.jwt.entity.Usuario;
import solarize.sptech.solarize.jwt.repository.UsuarioRepository;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;


    public void criar(Usuario novoUsuario){

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);

    }

    public UsuarioTokenDto autenticar(Usuario  usuario){

        final UsernamePasswordAuthenticationToken credencials = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credencials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(()-> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token =  gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);

    }

    public List<UsuarioListarDto> listarTodos(){

        List<Usuario> usuariosEncontrados = usuarioRepository.findAll();

        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();

    }


}
