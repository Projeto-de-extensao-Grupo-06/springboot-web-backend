package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.AutomaticBudgetConfigDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.AutomaticBudgetConfigResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.ConfigParameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automatic-budget-config")
@RequiredArgsConstructor
public class AutomaticBudgetConfigController {

    private final AutomaticBudgetConfigService automaticBudgetConfigService;

    @PreAuthorize("hasAuthority('CONFIGURATION_READ')")
    @GetMapping
    public ResponseEntity<AutomaticBudgetConfigResponseDto> getConfig() {
        AutomaticBudgetConfigResponseDto dto = automaticBudgetConfigService.getConfig();
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('CONFIGURATION_WRITE')")
    @PutMapping
    public ResponseEntity<AutomaticBudgetConfigResponseDto> saveConfig(
            @RequestBody @Valid AutomaticBudgetConfigDto dto
    ) {
        List<ConfigParameter> toSave = AutomaticBudgetConfigMapper.toEntityList(dto);
        AutomaticBudgetConfigResponseDto response = automaticBudgetConfigService.saveConfig(toSave);
        return ResponseEntity.ok(response);
    }
}