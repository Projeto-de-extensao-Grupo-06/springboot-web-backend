package com.solarize.solarizeWebBackend.modules.material;

import com.solarize.solarizeWebBackend.modules.material.dto.MaterialRequestDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialResponseDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialUrlRequestDto;
import com.solarize.solarizeWebBackend.modules.material.dto.MaterialUrlResponseDto;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
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




    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/materialUrl/{id}")
    public ResponseEntity<MaterialUrlResponseDto> getByIdMaterialUrl(@PathVariable Long id) {
        MaterialUrl url = service.getMaterialUrl(id);
        return ResponseEntity.ok(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/material/materialUrl/{materialId}")
    public ResponseEntity<List<MaterialUrlResponseDto>> listVisibleMaterialUrl(@PathVariable Long materialId) {
        List<MaterialUrl> urls = service.listMaterialUrlByMaterial(materialId);
        if (urls.isEmpty()) return ResponseEntity.noContent().build();

        List<MaterialUrlResponseDto> urlDtos = urls.stream()
                .map(MaterialMapper::toResponse)
                .toList();
        return ResponseEntity.ok(urlDtos);
    }

    @PreAuthorize("hasAuthority('BUDGET_WRITE')")
    @PostMapping("/materialUrl")
    public ResponseEntity<MaterialUrlResponseDto> createMaterialUrl(@Valid @RequestBody MaterialUrlRequestDto dto) {
        MaterialUrl url = service.createMaterialUrl(dto.getMaterialId(), dto.getUrl(), dto.getPrice());
        return ResponseEntity.status(201).body(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PutMapping("/materialUrl/{id}")
    public ResponseEntity<MaterialUrlResponseDto> updateMaterialUrl(
            @PathVariable Long id,
            @Valid @RequestBody MaterialUrlRequestDto dto
    ) {
        MaterialUrl updated = MaterialMapper.toEntity(dto, service.getMaterial(dto.getMaterialId()));
        MaterialUrl url = service.updateMaterialUrl(id, updated);
        return ResponseEntity.ok(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_DELETE')")
    @DeleteMapping("/materialUrl/{id}")
    public ResponseEntity<Void> deleteMaterialUrl(@PathVariable Long id) {
        service.deleteMaterialUrl(id);
        return ResponseEntity.noContent().build();
    }

}

