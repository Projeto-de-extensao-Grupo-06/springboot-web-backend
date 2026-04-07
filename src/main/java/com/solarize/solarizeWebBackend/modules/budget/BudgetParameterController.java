package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.BudgetParameterCreateDto;
//import com.solarize.solarizeWebBackend.modules.budget.dto.request.BudgetParameterFilterDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetParameterResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.ParameterOption;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget-parameters")
@RequiredArgsConstructor
public class BudgetParameterController {

    private final BudgetParameterService budgetParameterService;

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping
    public ResponseEntity<Page<BudgetParameterResponseDto>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isPreBudget,
            @RequestParam(required = false) String status,
            @PageableDefault(page = 0, size = 30) Pageable pageable
    ) {
        Page<BudgetParameter> result = budgetParameterService.getAll(search, isPreBudget, status, pageable);

        List<BudgetParameterResponseDto> dtos = result.getContent().stream().map(parameter -> {
            List<ParameterOption> options = budgetParameterService.getOptionsByParameterId(parameter.getId());
            return BudgetParameterMapper.toDto(parameter, options);
        }).toList();

        return ResponseEntity.ok(new PageImpl<>(dtos, pageable, result.getTotalElements()));
    }

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<BudgetParameterResponseDto> getById(@PathVariable Long id) {
        BudgetParameter parameter = budgetParameterService.getById(id);
        List<ParameterOption> options = budgetParameterService.getOptionsByParameterId(parameter.getId());
        return ResponseEntity.ok(BudgetParameterMapper.toDto(parameter, options));
    }

    @PreAuthorize("hasAuthority('BUDGET_WRITE')")
    @PostMapping
    public ResponseEntity<BudgetParameterResponseDto> create(@RequestBody @Valid BudgetParameterCreateDto dto) {
        BudgetParameter parameter = BudgetParameterMapper.toEntity(dto);

        List<ParameterOption> options = dto.getOptions().stream().map(o ->
                BudgetParameterMapper.toEntity(o, parameter)
        ).toList();

        BudgetParameter created = budgetParameterService.create(parameter, options);
        List<ParameterOption> savedOptions = budgetParameterService.getOptionsByParameterId(created.getId());
        return ResponseEntity.status(201).body(BudgetParameterMapper.toDto(created, savedOptions));
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<BudgetParameterResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid BudgetParameterCreateDto dto
    ) {
        BudgetParameter updated = BudgetParameterMapper.toEntity(dto);

        List<ParameterOption> options = dto.getOptions().stream().map(o ->
                BudgetParameterMapper.toEntity(o, updated)
        ).toList();

        BudgetParameter saved = budgetParameterService.update(id, updated, options);
        List<ParameterOption> savedOptions = budgetParameterService.getOptionsByParameterId(saved.getId());
        return ResponseEntity.ok(BudgetParameterMapper.toDto(saved, savedOptions));
    }

    @PreAuthorize("hasAuthority('BUDGET_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        budgetParameterService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('BUDGET_UPDATE')")
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        budgetParameterService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
