package com.solarize.solarizeWebBackend.modules.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecoveryPasswordOtpDto {
    @NotBlank
    @NotNull
    private String otp;

    @Email
    private String email;
}
