package com.solarize.solarize_web_backend.dto;

import solarize.sptech.solarize.jwt.entity.Usuario;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setSenha(usuarioCriacaoDto.getSenha());

        return usuario;

    }

    public static Usuario of(UsuarioLoginDto usuarioLoginDto){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setSenha(usuarioLoginDto.getSenha());

        return usuario;

    }

    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuarioTokenDto.getUserId());
        usuarioTokenDto.setEmail(usuarioTokenDto.getEmail());
        usuarioTokenDto.setNome(usuarioTokenDto.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;

    }

    public static UsuarioListarDto of(Usuario usuario){
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setId(usuario.getId());
        usuarioListarDto.setEmail(usuario.getEmail());
        usuarioListarDto.setNome(usuario.getNome());

        return usuarioListarDto;

    }

}
