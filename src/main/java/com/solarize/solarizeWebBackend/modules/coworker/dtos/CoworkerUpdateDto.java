package com.solarize.solarizeWebBackend.modules.coworker.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CoworkerUpdateDto {
    @NotBlank(message = "First Name is obligatory.")
    @Size(min = 3, max = 20)
    @Pattern(
            regexp = "^[a-zA-Z]+$", message = "This field only accepts letters."
    )
    private String firstName;

    @NotBlank(message = "Nickname or last name is obligatory.")
    @Size(min = 3, max = 20)
    @Pattern(
            regexp = "^[a-zA-Z]+$", message = "This field only accepts letters."
    )
    private String lastName;

    @Email
    @NotBlank(message = "E-mail is obligatory.")
    private String email;

    @NotBlank(message = "Phone is obligatory.")
    @Pattern(
            regexp = "^[1-9]{2}[9]?\\d{8}$",
            message = "Phone must be valid (10–15 digits, may include country code)"
    )
    private String phone;
}
