package com.solarize.solarize_web_backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solarize.sptech.solarize.jwt.dto.*;
import solarize.sptech.solarize.jwt.entity.Usuario;
import solarize.sptech.solarize.jwt.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){

        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);
        this.usuarioService.criar(novoUsuario);
        return ResponseEntity.status(201).build();

    }


    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){

        final Usuario usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);

    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioListarDto>> listarTodos(){

        List<UsuarioListarDto> usuariosEncontrados  = this.usuarioService.listarTodos();

        if (usuariosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuariosEncontrados);

    }

}
