package com.solarize.solarize_web_backend.modules.user;


import com.solarize.solarize_web_backend.modules.user.dtos.UserCreateDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserResponseDto;

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
