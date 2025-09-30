package com.solarize.solarize_web_backend.modules.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String token;

}
