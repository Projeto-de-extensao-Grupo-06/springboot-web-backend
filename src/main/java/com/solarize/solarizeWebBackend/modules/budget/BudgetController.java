package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.BudgetManualCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping("/projects/{projectId}/budget")
    public ResponseEntity<Void> createBudget(@PathVariable Long projectId, @RequestBody @Valid BudgetManualCreateDto dto) {
        Budget budget = BudgetMapper.toEntity(dto);
        Budget budgetCreated = budgetService.manualBudgetCreating(budget, projectId);

        return ResponseEntity.status(201).build();
    }
}
