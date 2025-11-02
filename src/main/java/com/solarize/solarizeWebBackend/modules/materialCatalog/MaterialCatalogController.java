package com.solarize.solarizeWebBackend.modules.materialCatalog;

import com.solarize.solarizeWebBackend.modules.materialCatalog.dto.MaterialCatalogResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialCatalogController {
    @GetMapping("/{id}")
    public ResponseEntity<MaterialCatalogResponseDTO> getMaterial(@PathVariable Long id){
        MaterialCatalogResponseDTO material = SERVICE.getMaterial(id);
        return ResponseEntity.ok(material);
    }
}
