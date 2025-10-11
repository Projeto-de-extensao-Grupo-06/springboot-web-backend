package com.solarize.solarizeWebBackend.modules.coworker.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
public class CoworkerCredentialsDto {

    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Schema(description = "Senha do usuário", example = "@Hf38HJ23vvl*d")
    private String password;
}
