package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.material.MaterialMapper;
import com.solarize.solarizeWebBackend.modules.material.MaterialService;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlRequestDto;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDto;
import com.solarize.solarizeWebBackend.modules.materialUrl.model.MaterialUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materialUrls")
public class MaterialUrlController {
    private final MaterialUrlService service;
    private final MaterialService serviceMaterial;

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialUrlResponseDto> getByIdMaterialUrl(@PathVariable Long id) {
        MaterialUrl url = service.getMaterialUrl(id);
        return ResponseEntity.ok(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<MaterialUrlResponseDto>> listVisibleMaterialUrl(@PathVariable Long materialId) {
        List<MaterialUrl> urls = service.listMaterialUrlByMaterial(materialId);
        if (urls.isEmpty()) return ResponseEntity.noContent().build();

        List<MaterialUrlResponseDto> urlDtos = urls.stream()
                .map(MaterialMapper::toResponse)
                .toList();
        return ResponseEntity.ok(urlDtos);
    }

    @PreAuthorize("hasAuthority('BUDGET_WRITE')")
    @PostMapping
    public ResponseEntity<MaterialUrlResponseDto> createMaterialUrl(@Valid @RequestBody MaterialUrlRequestDto dto) {
        MaterialUrl url = service.createMaterialUrl(dto.getMaterialId(), dto.getUrl(), dto.getPrice());
        return ResponseEntity.status(201).body(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialUrlResponseDto> updateMaterialUrl(
            @PathVariable Long id,
            @Valid @RequestBody MaterialUrlRequestDto dto
    ) {
        MaterialUrl updated = MaterialMapper.toEntity(dto, serviceMaterial.getMaterial(dto.getMaterialId()));
        MaterialUrl url = service.updateMaterialUrl(id, updated);
        return ResponseEntity.ok(MaterialMapper.toResponse(url));
    }

    @PreAuthorize("hasAuthority('BUDGET_DELETE')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMaterialUrl(@PathVariable Long id) {
        service.deleteMaterialUrl(id);
        return ResponseEntity.noContent().build();
    }
}
