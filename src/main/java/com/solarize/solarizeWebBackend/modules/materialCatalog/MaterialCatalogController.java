package com.solarize.solarizeWebBackend.modules.materialCatalog;

import com.solarize.solarizeWebBackend.modules.materialCatalog.dto.MaterialCatalogResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialCatalogController {

    private final MaterialCatalogService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialCatalogResponseDTO>> listAll() {
        List<MaterialCatalogResponseDTO> materials = materialService.findAll();
        if (materials.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialCatalogResponseDTO> getById(@PathVariable Long id) {
        MaterialCatalogResponseDTO material = materialService.getById(id);
        return ResponseEntity.ok(material);
    }
}

