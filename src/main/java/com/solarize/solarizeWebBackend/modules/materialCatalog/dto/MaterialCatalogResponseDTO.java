package com.solarize.solarizeWebBackend.modules.materialCatalog.dto;

import com.solarize.solarizeWebBackend.modules.materialCatalog.MetricEnum;
import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrlResponseDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialCatalogResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String supplier;
    private MetricEnum metric;
    private List<MaterialUrlResponseDTO> urls;
}
