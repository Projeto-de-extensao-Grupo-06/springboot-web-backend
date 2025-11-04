package com.solarize.solarizeWebBackend.modules.materialCatalog;

import com.solarize.solarizeWebBackend.modules.materialCatalog.dto.MaterialCatalogMapper;
import com.solarize.solarizeWebBackend.modules.materialCatalog.dto.MaterialCatalogResponseDTO;
import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialCatalogService {

    private final MaterialCatalogRepository materialCatalogRepository;
    private final MaterialUrlRepository materialUrlRepository;

    public List<MaterialCatalogResponseDTO> findAll() {
        List<MaterialCatalog> materials = materialCatalogRepository.findAll();
        return MaterialCatalogMapper.toResponseList(materials);
    }

    public MaterialCatalogResponseDTO getById(Long id) {
        MaterialCatalog material = materialCatalogRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Material não encontrado"));
        return MaterialCatalogMapper.toResponseDTO(material);
    }
}
