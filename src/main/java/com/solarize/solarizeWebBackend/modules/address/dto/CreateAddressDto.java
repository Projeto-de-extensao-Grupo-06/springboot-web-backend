package com.solarize.solarizeWebBackend.modules.address.dto;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.enumerated.TypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class CreateAddressDto {
    @NotBlank
    private String postalCode;

    @NotBlank
    private String streetName;

    private String number;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String city;

    @NotNull
    private BrazilianState state;

    @NotNull
    private TypeEnum type;
    private String apartment;
}
