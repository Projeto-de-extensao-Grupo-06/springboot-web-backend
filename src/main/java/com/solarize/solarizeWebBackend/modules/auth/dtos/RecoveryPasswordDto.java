package com.solarize.solarizeWebBackend.modules.auth.dtos;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecoveryPasswordDto {
    @Email
    private String email;
}
