package com.solarize.solarizeWebBackend.modules.materialUrl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MaterialUrlResponseDTO {
    private Long id;
    private String description;
    private String url;
}
