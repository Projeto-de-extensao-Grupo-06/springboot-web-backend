package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetMaterialRepository;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.DocumentTypeEnum;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialUrlRequestDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialUrlResponseDto;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialRepository;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialUrlRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialUrlRepository materialUrlRepository;
    private final BudgetMaterialRepository budgetMaterialRepository;

    public Boolean allMaterialsUrlExists(List<Long> materialUrlIds) {
        int qtdExists = materialUrlRepository.allExistsByIdIn(materialUrlIds);
        return qtdExists == materialUrlIds.size();
    }


    public Material getMaterial(Long id) {
        return materialRepository.findByIdAndHiddenFalse(id)
                .orElseThrow(() -> new NotFoundException("Material not found."));
    }

    public List<Material> getMaterials() {
        return materialRepository.findAllByHiddenFalse();
    }

    public Material postMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long id, Material updated) {
        Material existing = getMaterial(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setMetric(updated.getMetric());
        return materialRepository.save(existing);
    }

    public void deleteMaterial(Long id) {
        Material material = getMaterial(id);
        List<MaterialUrl> urls = materialUrlRepository.findAllByMaterialId(id);

        boolean UrlUsed = false;
        for (MaterialUrl url : urls) {
            if (budgetMaterialRepository.existsByMaterialUrl_Id(url.getId())) {
                UrlUsed  = true;
            }
        }

        if (UrlUsed) {
            for (MaterialUrl url : urls) {
                url.setHidden(true);
            }
            material.setHidden(true);

            materialUrlRepository.saveAll(urls);
            materialRepository.save(material);
        } else {
            materialUrlRepository.deleteAll(urls);
            materialRepository.delete(material);
        }
    }


    public MaterialUrl createMaterialUrl(Long materialId, String url, Double price) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        MaterialUrl materialUrl = new MaterialUrl();
        materialUrl.setMaterial(material);
        materialUrl.setUrl(url);
        materialUrl.setPrice(price);

        return materialUrlRepository.save(materialUrl);
    }

    public MaterialUrl getMaterialUrl(Long id) {
        return materialUrlRepository.findByIdAndHiddenFalse(id)
                .orElseThrow(() -> new NotFoundException("MaterialUrl not found or hidden"));
    }

    public List<MaterialUrl> listMaterialUrlByMaterial(Long materialId) {
        return materialUrlRepository.findAllByMaterialIdAndHiddenFalse(materialId);
    }

    public MaterialUrl updateMaterialUrl(Long id, MaterialUrl updated) {
        MaterialUrl existing = materialUrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MaterialUrl not found"));

        existing.setUrl(updated.getUrl());
        existing.setPrice(updated.getPrice());

        return materialUrlRepository.save(existing);
    }

    public void deleteMaterialUrl(Long id) {
        MaterialUrl url = materialUrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MaterialUrl not found"));

        boolean Budget = budgetMaterialRepository.existsByMaterialUrl_Id(id);

        if (Budget) {
            url.setHidden(true);
            materialUrlRepository.save(url);
        } else {
            materialUrlRepository.delete(url);
        }
    }

}
