package com.solarize.solarizeWebBackend.modules.materialUrl.dto;

import com.solarize.solarizeWebBackend.modules.materialCatalog.MaterialCatalog;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialUrlResponseDTO {
    private String description;
    private String url;
}
