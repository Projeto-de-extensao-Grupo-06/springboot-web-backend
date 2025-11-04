package com.solarize.solarizeWebBackend.modules.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Address", description = "Address data")
public class AddressDTO {
    @Schema(description = "Address identifier", example = "5")
    private Long id;

    @Schema(description = "Postal code (CEP)", example = "01001-000")
    @NotBlank(message = "Postal code is obligatory")
    private String postalCode;

    @Schema(description = "Street name", example = "Praça da Sé")
    @NotBlank(message = "Street name is obligatory")
    private String streetName;

    @Schema(description = "House or building number", example = "100")
    private String number;

    @Schema(description = "Neighborhood name", example = "Centro")
    private String neighborhood;

    @Schema(description = "City name", example = "São Paulo")
    @NotBlank(message = "City is obligatory")
    private String city;

    @Schema(description = "State (abbreviation)", example = "SP")
    @NotBlank(message = "State is obligatory")
    private String state;

    @Schema(description = "Type of address (RESIDENTIAL, COMMERCIAL, BUILDING, etc.)", example = "RESIDENTIAL")
    private TypeEnum type;
}
