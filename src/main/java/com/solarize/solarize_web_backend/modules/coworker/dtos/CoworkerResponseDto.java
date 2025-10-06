package com.solarize.solarize_web_backend.modules.coworker.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoworkerResponseDto {
    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário", example = "John")
    private String firstName;

    @Schema(description = "Sobrenome do usuário", example = "Doe")
    private String lastName;

    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Schema(description = "Telefone do usuário", example = "11969166583")
    private String phone;

}
