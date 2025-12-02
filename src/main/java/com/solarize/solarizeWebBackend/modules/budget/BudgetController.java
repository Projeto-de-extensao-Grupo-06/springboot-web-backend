package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.*;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PreAuthorize("hasAuthority('BUDGET_CREATE')")
    @PostMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> createBudget(@PathVariable Long projectId, @RequestBody @Valid BudgetManualCreateDto dto) {
        Budget budget = BudgetMapper.toEntity(dto);
        Budget budgetCreated = budgetService.manualBudgetCreating(budget, projectId);

        return ResponseEntity.status(201).body(BudgetMapper.toDto(budgetCreated));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PatchMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> updateBudget(@RequestBody @Valid BudgetPatchDto dto, @PathVariable Long projectId) {
        Budget budget = BudgetMapper.toEntity(dto);
        Budget budgetUpdated = budgetService.updateBudget(budget, projectId);

        return ResponseEntity.ok(BudgetMapper.toDto(budgetUpdated));
    }

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/projects/{projectId}/budget")
    public ResponseEntity<BudgetResponseDto> getBudget(@PathVariable Long projectId) {
        return ResponseEntity.ok(BudgetMapper.toDto(budgetService.getBudget(projectId)));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PatchMapping("/projects/{projectId}/budget/material")
    public ResponseEntity<BudgetResponseDto> updateMaterial(@PathVariable Long projectId, @RequestBody @Valid UpdateMaterialDto dto) {
        Budget budget = budgetService.updateMaterial(BudgetMapper.toEntity(dto), projectId);
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PatchMapping("/projects/{projectId}/budget/parameters/fixed")
    public ResponseEntity<BudgetResponseDto> updateFixedParameters(@PathVariable Long projectId, @RequestBody @Valid UpdateFixedParametersDto dto) {
        Budget budget = budgetService.updateFixedParameter(projectId, BudgetMapper.toEntity(dto));
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PatchMapping("/projects/{projectId}/budget/parameters/personalized")
    public ResponseEntity<BudgetResponseDto> updatePersonalizedParameters(@PathVariable Long projectId, @RequestBody @Valid UpdatePersonalizedParametersDto dto) {
        Budget budget = budgetService.updatePersonalizedParameter(projectId, BudgetMapper.toEntity(dto));
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }


    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @DeleteMapping("/budgets/{budgetId}/materials/{materialId}")
    public ResponseEntity<BudgetResponseDto> deleteMaterials(@PathVariable Long budgetId, @PathVariable Long materialId) {
        Budget budget = budgetService.deleteMaterial(budgetId, materialId);
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @DeleteMapping("/budgets/{budgetId}/parameters/fixed/{parameterName}")
    public ResponseEntity<BudgetResponseDto> deleteFixedParameter(@PathVariable Long budgetId, @PathVariable FixedParameterName parameterName) {
        Budget budget = budgetService.deleteFixedParameter(budgetId, parameterName);
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @DeleteMapping("/budgets/{budgetId}/parameters/personalized/{parameterId}")
    public ResponseEntity<BudgetResponseDto> deleteFixedParameter(@PathVariable Long budgetId, @PathVariable Long parameterId) {
        Budget budget = budgetService.deletePersonalizedParameter(budgetId, parameterId);
        return ResponseEntity.ok(BudgetMapper.toDto(budget));
    }
}
