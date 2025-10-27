package com.solarize.solarizeWebBackend.modules.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Create Client", description = "Object with the necessary data to create a client")
public class CreateClientDTO {
    @Schema(description = "Client first name", example = "John")
    @NotBlank(message = "First name is obligatory")
    private String firstName;

    @Schema(description = "Client nickname or last name", example = "Doe")
    @NotBlank(message = "Nick name is obligatory")
    private String lastName;

    @Schema(description = "Document number", example = "91280161060")
    @NotBlank(message = "Document number is obligatory")
    @Pattern(regexp = "\\d{5,15}", message = "Document number must contain only digits with length between 5 and 15")
    private String documentNumber;

    @Schema(description = "Type of document (CPF, RG, CNPJ or PASSPORT)", example = "CPF")
    @NotBlank(message = "Document type is obligatory")
    private String documentType;

    @Schema(description = "CNPJ of the client", example = "59603348000116")
    @Pattern(regexp = "\\d{14}", message = "CNPJ must contain 14 digits")
    private String cnpj;

    @Schema(description = "Additional notes/description", example = "VIP client, handle with priority")
    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;

    @Schema(description = "Client phone number", example = "+5511949902159")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone must be valid (10–15 digits, may include country code)")
    private String phone;

    @Schema(description = "Client email address", example = "john.doe@example.com")
    @Email(message = "Invalid e-mail")
    @NotBlank(message = "E-mail is obligatory")
    private String email;

    @Schema(description = "ID of the coworker responsible for last update", example = "3")
    private Long coworkerLastUpdateId;

    @Schema(description = "ID of the main address associated with the client", example = "12")
    private Long mainAddressId;
}
