package com.solarize.solarizeWebBackend.modules.auth.dtos;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor    // Cria um construtor sem argumentos (ex: new MinhaClasse())
@AllArgsConstructor   // Cria um construtor com todos os argumentos
@Builder
public class AuthResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String token;
    private List<? extends GrantedAuthority> authorities;
}
