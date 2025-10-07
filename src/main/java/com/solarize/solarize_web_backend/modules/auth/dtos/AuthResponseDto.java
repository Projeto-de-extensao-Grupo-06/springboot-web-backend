package com.solarize.solarize_web_backend.modules.auth.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Setter
@Getter
public class AuthResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String token;
    private List<? extends GrantedAuthority> authorities;
}
