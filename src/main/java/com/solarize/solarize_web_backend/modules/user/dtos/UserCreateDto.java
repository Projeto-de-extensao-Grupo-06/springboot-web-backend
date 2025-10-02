package com.solarize.solarize_web_backend.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDto {
    @Size(min = 3, max = 20)
    @Schema(description = "Nome do usuário", example = "John")
    private String firstName;

    @Size(min = 3, max = 20)
    @Schema(description = "Sobrenome do usuário", example = "Doe")
    private String lastName;

    @Email
    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Pattern(
            regexp = "^[1-9]{2}[9]?\\d{8}$",
            message = "Telefone inválido. Apenas números, DDD + número."
    )
    @Schema(description = "Apenas números, DDD + número.", example = "(11)960976085")
    private String phone;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[\\S]{8,}$",
            message = "Senha fraca. Deve ter mínimo 8 caracteres, ao menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
    )
    @Schema(
            description = "Senha do usuário. Deve ter mínimo 8 caracteres, ao menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.",
            example = "@Hf38HJ23vvl*d"
    )
    private String password;




}
