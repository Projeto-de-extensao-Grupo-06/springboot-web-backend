package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.client.ClientMapper;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialUrlService {
    private final MaterialUrlRepository REPOSITORY;

    public MaterialUrlResponseDTO getMaterialUrl(Long id){
        Optional<MaterialUrl> materialUrl = REPOSITORY.findById(id);
        if(materialUrl.isEmpty()) throw new NotFoundException("Material not found.");
        return MaterialUrlMapper.of(materialUrl.get());
    }

}

