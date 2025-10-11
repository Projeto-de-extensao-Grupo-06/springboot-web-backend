package com.solarize.solarizeWebBackend.modules.auth;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerCredentialsDto;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class AuthMapper {
    public static AuthResponseDto of(Coworker coworker, String token, List<? extends GrantedAuthority> authorities){
        AuthResponseDto authResponseDto = new AuthResponseDto();

        authResponseDto.setUserId(coworker.getId());
        authResponseDto.setFirstName(coworker.getFirstName());
        authResponseDto.setLastName(coworker.getLastName());
        authResponseDto.setToken(token);
        authResponseDto.setAuthorities(authorities);

        return authResponseDto;
    }

    public static Coworker of(CoworkerCredentialsDto coworkerCredentialsDto) {
        Coworker coworker = new Coworker();

        coworker.setEmail(coworkerCredentialsDto.getEmail());
        coworker.setPassword(coworkerCredentialsDto.getPassword());

        return coworker;
    }
}
