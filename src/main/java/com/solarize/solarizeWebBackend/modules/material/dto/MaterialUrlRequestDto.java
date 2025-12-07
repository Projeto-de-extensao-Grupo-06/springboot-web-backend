package com.solarize.solarizeWebBackend.modules.material.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialUrlRequestDto {
    private String url;
    private Double price;
    private Long materialId;
}
