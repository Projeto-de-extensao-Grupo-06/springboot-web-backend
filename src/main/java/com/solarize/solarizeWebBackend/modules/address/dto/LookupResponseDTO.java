package com.solarize.solarizeWebBackend.modules.address.dto;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LookupResponseDTO {
    private BrazilianState state;
    private List<String> cities;
}
