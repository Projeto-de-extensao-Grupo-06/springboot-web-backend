package com.solarize.solarize_web_backend.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoworkerCredentialsDto {

    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Schema(description = "Senha do usuário", example = "@Hf38HJ23vvl*d")
    private String password;
}
