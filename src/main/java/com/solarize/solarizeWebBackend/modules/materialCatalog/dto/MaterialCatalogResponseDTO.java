package com.solarize.solarizeWebBackend.modules.materialCatalog.dto;

import com.solarize.solarizeWebBackend.modules.materialCatalog.MetricEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialCatalogResponseDTO {
    private String name;
    private Double price;
}
