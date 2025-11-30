package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.BudgetManualCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.request.BudgetPatchDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BudgetResponseDto> addMaterial(@PathVariable Long projectId) {

        return null;
    }
}
