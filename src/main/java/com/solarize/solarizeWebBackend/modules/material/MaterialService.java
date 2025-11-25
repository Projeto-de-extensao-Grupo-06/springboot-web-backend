package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialRepository;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialUrlRepository materialUrlRepository;

    public Boolean allMaterialsUrlExists(List<Long> materialUrlIds) {
        int qtdExists = materialUrlRepository.allExistsByIdIn(materialUrlIds);
        return qtdExists == materialUrlIds.size();
    }

    public List<MaterialUrl> findMaterialByIdIn(List<Long> ids) {
        return materialUrlRepository.findAllByIdIn(ids);
    }
}
