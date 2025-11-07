package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.client.ClientMapper;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDTO;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialWithUrlsResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialUrlService {

    private final MaterialUrlRepository repository;

    public MaterialWithUrlsResponseDTO getMaterialWithUrls(Long materialId) {
        List<MaterialUrl> urls = repository.findByMaterial_Id(materialId);

        if (urls.isEmpty()) {
            throw new NotFoundException("Material not found.");
        }

        return MaterialWithUrlsResponseDTO.builder()
                .id(urls.get(0).getMaterial().getId())
                .name(urls.get(0).getMaterial().getName())
                .links(urls.stream()
                        .map(u -> MaterialUrlResponseDTO.builder()
                                .description(u.getDescription())
                                .url(u.getUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

