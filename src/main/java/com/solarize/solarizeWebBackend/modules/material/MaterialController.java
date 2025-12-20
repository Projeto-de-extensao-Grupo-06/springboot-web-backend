package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.material.dto.MaterialRequestDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialResponseDto;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService service;

        @PreAuthorize("hasAuthority('BUDGET_READ')")
        @GetMapping("/{id}")
        public ResponseEntity<MaterialResponseDto> getMaterial(@PathVariable Long id) {
            Material material = service.getMaterial(id);
            return ResponseEntity.ok(MaterialMapper.toResponse(material));
        }

        @PreAuthorize("hasAuthority('BUDGET_READ')")
        @GetMapping
        public ResponseEntity<List<MaterialResponseDto>> getMaterials() {
            List<Material> materials = service.getMaterials();
            if (materials.isEmpty()) return ResponseEntity.noContent().build();

            List<MaterialResponseDto> materialDtos = materials.stream()
                    .map(MaterialMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(materialDtos);
        }

        @PreAuthorize("hasAuthority('BUDGET_WRITE')")
        @PostMapping
        public ResponseEntity<MaterialResponseDto> createMaterial(
                @Valid @RequestBody MaterialRequestDto dto
        ) {
            Material created = service.postMaterial(MaterialMapper.toEntity(dto));
            return ResponseEntity.status(201).body(MaterialMapper.toResponse(created));
        }

        @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
        @PutMapping("/{id}")
        public ResponseEntity<MaterialResponseDto> updateMaterial(
                @PathVariable Long id,
                @Valid @RequestBody MaterialRequestDto dto
        ) {
            Material updated = service.updateMaterial(id, MaterialMapper.toEntity(dto));
            return ResponseEntity.ok(MaterialMapper.toResponse(updated));
        }

        @PreAuthorize("hasAuthority('BUDGET_DELETE')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
            service.deleteMaterial(id);
            return ResponseEntity.noContent().build();
        }


}

