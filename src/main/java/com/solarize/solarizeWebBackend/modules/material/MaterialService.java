package com.solarize.solarizeWebBackend.modules.material;

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
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialUrlRepository materialUrlRepository;
    private final BudgetMaterialRepository budgetMaterialRepository;

    public Material getMaterial(Long id) {
        return materialRepository.findByIdAndHiddenFalse(id)
                .orElseThrow(() -> new NotFoundException("Material not found."));
    }

    public List<Material> getMaterials() {
        return materialRepository.findAllByHiddenFalse();
    }

    public Material postMaterial(Material material) {
        if (materialRepository.existsByName(material.getName())) {
            throw new ConflictException("This material name is already registered.");
        }
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

        boolean urlUsed = false;
        for (MaterialUrl url : urls) {
            if (budgetMaterialRepository.existsByMaterialUrl_Id(url.getId())) {
                urlUsed = true;
            }
        }

        if (urlUsed) {
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
}
