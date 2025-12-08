package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetMaterialRepository;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import com.solarize.solarizeWebBackend.modules.materialUrl.model.MaterialUrl;
import com.solarize.solarizeWebBackend.modules.material.repository.MaterialRepository;
import com.solarize.solarizeWebBackend.modules.materialUrl.repository.MaterialUrlRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MaterialUrlService {
    private final MaterialRepository materialRepository;
    private final MaterialUrlRepository materialUrlRepository;
    private final BudgetMaterialRepository budgetMaterialRepository;

    public Boolean allMaterialsUrlExists(List<Long> materialUrlIds) {
        int qtdExists = materialUrlRepository.allExistsByIdIn(materialUrlIds);
        return qtdExists == materialUrlIds.size();
    }

    public MaterialUrl createMaterialUrl(Long materialId, String url, Double price) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new NotFoundException("Material not found"));

        if (materialUrlRepository.existsByUrl(url)) {
            throw new ConflictException("This URL is already registered.");
        }
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

        boolean budget = budgetMaterialRepository.existsByMaterialUrl_Id(id);

        if (budget) {
            url.setHidden(true);
            materialUrlRepository.save(url);
        } else {
            materialUrlRepository.delete(url);
        }
    }
}
