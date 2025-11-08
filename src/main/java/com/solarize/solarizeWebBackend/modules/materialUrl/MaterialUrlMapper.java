package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDTO;

public class MaterialUrlMapper {
    public static MaterialUrlResponseDTO of (MaterialUrl materialUrl){
        if(materialUrl == null) return null;
        return MaterialUrlResponseDTO.builder()
                .description(materialUrl.getDescription())
                .url(materialUrl.getUrl())
                .build();
    }
}
