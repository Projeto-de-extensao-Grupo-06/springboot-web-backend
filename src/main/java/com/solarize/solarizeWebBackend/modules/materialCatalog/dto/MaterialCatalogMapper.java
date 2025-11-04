package com.solarize.solarizeWebBackend.modules.materialCatalog.dto;

import com.solarize.solarizeWebBackend.modules.materialCatalog.MaterialCatalog;
import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrlResponseDTO;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MaterialCatalogMapper {
    public static MaterialCatalogResponseDTO toResponseDTO(MaterialCatalog material) {
        List<MaterialUrlResponseDTO> urls = material.getUrls().stream()
                .map(url -> new MaterialUrlResponseDTO(
                        url.getId(),
                        url.getDescription(),
                        url.getUrl()
                ))
                .collect(Collectors.toList());

        return new MaterialCatalogResponseDTO(
                material.getId(),
                material.getName(),
                material.getDescription(),
                material.getSupplier(),
                material.getMetric(),
                urls
        );
    }

    public static List<MaterialCatalogResponseDTO> toResponseList(List<MaterialCatalog> materials) {
        return materials.stream()
                .map(MaterialCatalogMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
