package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDTO;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialWithUrlsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialUrlController {
    private final MaterialUrlService SERVICE;

    @GetMapping("/{id}")
    public ResponseEntity<MaterialWithUrlsResponseDTO> getMaterialUrls(@PathVariable Long id) {
        MaterialWithUrlsResponseDTO material = SERVICE.getMaterialWithUrls(id);
        return ResponseEntity.ok(material);
    }

}
