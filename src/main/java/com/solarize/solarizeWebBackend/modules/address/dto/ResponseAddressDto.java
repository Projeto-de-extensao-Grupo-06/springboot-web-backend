package com.solarize.solarizeWebBackend.modules.address.dto;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.enumerated.TypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseAddressDto {
    private Long id;
    private String postalCode;
    private String streetName;
    private String number;
    private String neighborhood;
    private String city;
    private BrazilianState state;
    private String stateName;
    private TypeEnum type;
    private String apartment;
}
