package com.solarize.solarizeWebBackend.modules.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Client Response", description = "Client response data")
public class ClientResponseDTO {

    @Schema(description = "Client identifier number", example = "101")
    private Long id;

    @Schema(description = "Client first name", example = "John")
    private String firstName;

    @Schema(description = "Client last name", example = "Doe")
    private String lastName;

    @Schema(description = "Client phone number", example = "+5511998765432")
    private String phone;

    @Schema(description = "Client e-mail address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Client document number", example = "91280161060")
    private String documentNumber;

    @Schema(description = "Type of document (CPF, RG, CNPJ or PASSPORT)", example = "CPF")
    private String documentType;

    @Schema(description = "CNPJ of the client", example = "59603348000116")
    private String cnpj;

    @Schema(description = "Additional notes/description", example = "VIP client, handle with priority")
    private String note;

    @Schema(description = "Date and time when the client was created")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the client was last updated")
    private LocalDateTime updatedAt;

}
