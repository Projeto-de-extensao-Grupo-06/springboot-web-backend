package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.material.dto.MaterialRequestDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialResponseDto;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlRequestDto;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDto;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import com.solarize.solarizeWebBackend.modules.materialUrl.model.MaterialUrl;

public class MaterialMapper {
    public static Material toEntity(MaterialRequestDto dto) {
        Material material = new Material();
        material.setName(dto.getName());
        material.setDescription(dto.getDescription());
        material.setMetric(dto.getMetric());
        return material;
    }

    public static MaterialResponseDto toResponse(Material material) {
        return MaterialResponseDto.builder()
                .id(material.getId())
                .name(material.getName())
                .description(material.getDescription())
                .metric(material.getMetric())
                .build();
    }
    public static MaterialUrl toEntity(MaterialUrlRequestDto dto, Material material) {
        MaterialUrl url = new MaterialUrl();
        url.setUrl(dto.getUrl());
        url.setPrice(dto.getPrice());
        url.setMaterial(material);
        url.setHidden(false);
        return url;
    }

    public static MaterialUrlResponseDto toResponse(MaterialUrl url) {
        return MaterialUrlResponseDto.builder()
                .id(url.getId())
                .url(url.getUrl())
                .price(url.getPrice())
                .build();
    }
}
