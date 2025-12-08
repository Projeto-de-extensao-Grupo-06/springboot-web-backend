package com.solarize.solarizeWebBackend.modules.materialUrl.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MaterialUrlResponseDto {
    private Long id;
    private String url;
    private Double price;
}
