package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.user.User;
import com.solarize.solarize_web_backend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserCredentialsDto;

public class AuthMapper {
    public static AuthResponseDto of(User user, String token){
        AuthResponseDto authResponseDto = new AuthResponseDto();

        authResponseDto.setUserId(user.getId());
        authResponseDto.setFirstName(user.getFirstName());
        authResponseDto.setLastName(user.getLastName());
        authResponseDto.setToken(token);

        return authResponseDto;
    }

    public static User of(UserCredentialsDto userCredentialsDto) {
        User user = new User();

        user.setEmail(userCredentialsDto.getEmail());
        user.setPassword(userCredentialsDto.getPassword());

        return user;
    }
}
