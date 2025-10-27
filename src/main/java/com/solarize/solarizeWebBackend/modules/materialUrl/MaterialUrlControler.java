package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.materialUrl.dto.MaterialUrlResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialUrlControler {
    private final MaterialUrlService SERVICE;

    @GetMapping("/{id}")
    public ResponseEntity<MaterialUrlResponseDTO> getMaterialUrl(@PathVariable Long id){
        final MaterialUrlResponseDTO materialUrl = SERVICE.getMaterialUrl(id);
        return ResponseEntity.ok(materialUrl);
    }


}
