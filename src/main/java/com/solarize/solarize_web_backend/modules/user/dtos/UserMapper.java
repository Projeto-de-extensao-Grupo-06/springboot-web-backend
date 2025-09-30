package com.solarize.solarize_web_backend.modules.user.dtos;


import com.solarize.solarize_web_backend.modules.user.User;

public class UserMapper {

    public static User of(UserCreateDto userCreateDto){
        User user = new User();

        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setPhone(userCreateDto.getPhone());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());

        return user;

    }

    public static User of(UserCredentialsDto userCredentialsDto){
        User user = new User();

        user.setEmail(userCredentialsDto.getEmail());
        user.setPassword(userCredentialsDto.getPassword());

        return user;
    }

    public static AuthResponseDto of(User user, String token){
        AuthResponseDto authResponseDto = new AuthResponseDto();

        authResponseDto.setUserId(user.getId());
        authResponseDto.setFirstName(user.getFirstName());
        authResponseDto.setLastName(user.getLastName());
        authResponseDto.setToken(token);

        return authResponseDto;
    }

    public static UserResponseDto of(User user){
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setPhone(user.getPhone());

        return userResponseDto;

    }

}
