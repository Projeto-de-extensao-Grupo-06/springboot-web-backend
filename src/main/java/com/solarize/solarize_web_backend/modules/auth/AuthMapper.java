package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.user.Coworker;
import com.solarize.solarize_web_backend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.CoworkerCredentialsDto;

public class AuthMapper {
    public static AuthResponseDto of(Coworker coworker, String token){
        AuthResponseDto authResponseDto = new AuthResponseDto();

        authResponseDto.setUserId(coworker.getId());
        authResponseDto.setFirstName(coworker.getFirstName());
        authResponseDto.setLastName(coworker.getLastName());
        authResponseDto.setToken(token);

        return authResponseDto;
    }

    public static Coworker of(CoworkerCredentialsDto coworkerCredentialsDto) {
        Coworker coworker = new Coworker();

        coworker.setEmail(coworkerCredentialsDto.getEmail());
        coworker.setPassword(coworkerCredentialsDto.getPassword());

        return coworker;
    }
}
