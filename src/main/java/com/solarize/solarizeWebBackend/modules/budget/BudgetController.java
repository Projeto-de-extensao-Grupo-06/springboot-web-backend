package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.*;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> createBudget(@PathVariable Long projectId, @RequestBody @Valid BudgetManualCreateDto dto) {
        Budget budget = BudgetMapper.toEntity(dto);
        Budget budgetCreated = budgetService.manualBudgetCreating(budget, projectId);

        return ResponseEntity.status(201).body(BudgetMapper.toDto(budgetCreated));
    }

    @PatchMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> updateBudget(@RequestBody @Valid BudgetPatchDto dto, @PathVariable Long projectId) {
        Budget budget = BudgetMapper.toEntity(dto);
        Budget budgetUpdated = budgetService.updateBudget(budget, projectId);

        return ResponseEntity.ok(BudgetMapper.toDto(budgetUpdated));
    }

    @GetMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> getBudget(@PathVariable Long projectId) {
        return ResponseEntity.ok(BudgetMapper.toDto(budgetService.getBudget(projectId)));
    }

    @PatchMapping("/projects/{projectId}/budget/material")
    public ResponseEntity<BudgetResponseDto> updateMaterial(@PathVariable Long projectId, @RequestBody @Valid UpdateMaterialDto dto) {
        Budget budget = budgetService.updateMaterial(BudgetMapper.toEntity(dto), projectId);
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PatchMapping("/projects/{projectId}/budget/parameters/fixed")
    public ResponseEntity<BudgetResponseDto> updateFixedParameters(@PathVariable Long projectId, @RequestBody @Valid UpdateFixedParametersDto dto) {
        Budget budget = budgetService.updateFixedParameter(projectId, BudgetMapper.toEntity(dto));
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PatchMapping("/projects/{projectId}/budget/parameters/personalized")
    public ResponseEntity<BudgetResponseDto> updatePersonalizedParameters(@PathVariable Long projectId, @RequestBody @Valid UpdatePersonalizedParametersDto dto) {
        Budget budget = budgetService.updatePersonalizedParameter(projectId, BudgetMapper.toEntity(dto));
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }
}
