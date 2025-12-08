package com.solarize.solarizeWebBackend.modules.materialUrl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialUrlRequestDto {
    private String url;
    private Double price;
    private Long materialId;
}
