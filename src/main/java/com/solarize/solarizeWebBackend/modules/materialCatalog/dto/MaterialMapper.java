package com.solarize.solarizeWebBackend.modules.materialCatalog.dto;

import com.solarize.solarizeWebBackend.modules.materialCatalog.MaterialCatalog;

public class MaterialMapper {
    public static MaterialCatalogResponseDTO of (MaterialCatalog materialCatalog){
        if(materialCatalog == null) return null;
        return MaterialCatalogResponseDTO.builder()
                .name(materialCatalog.getName())
                .price(materialCatalog.getPrice())
                .build();
    }
}
