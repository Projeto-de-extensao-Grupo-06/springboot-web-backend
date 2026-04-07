package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.BudgetParameterCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.request.ParameterOptionCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetParameterResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.ParameterOptionResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.ParameterOption;

import java.util.List;

public class BudgetParameterMapper {

    public static BudgetParameter toEntity(BudgetParameterCreateDto dto) {
        if (dto == null) return null;

        return BudgetParameter.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .metric(dto.getMetric())
                .isPreBudget(dto.getIsPreBudget())
                .fixedValue(dto.getFixedValue())
                .build();
    }

    public static ParameterOption toEntity(ParameterOptionCreateDto dto, BudgetParameter parameter) {
        return ParameterOption.builder()
                .type(dto.getType())
                .additionTax(dto.getAdditionTax())
                .fixedCost(dto.getFixedCost())
                .budgetParameter(parameter)
                .build();
    }

    public static BudgetParameterResponseDto toDto(BudgetParameter parameter, List<ParameterOption> options) {
        List<ParameterOptionResponseDto> optionDtos = options.stream().map(o ->
                ParameterOptionResponseDto.builder()
                        .id(o.getId())
                        .type(o.getType())
                        .additionTax(o.getAdditionTax())
                        .fixedCost(o.getFixedCost())
                        .build()
        ).toList();

        return BudgetParameterResponseDto.builder()
                .id(parameter.getId())
                .name(parameter.getName())
                .description(parameter.getDescription())
                .metric(parameter.getMetric())
                .isPreBudget(parameter.getIsPreBudget())
                .fixedValue(parameter.getFixedValue())
                .active(parameter.getActive())
                .createdAt(parameter.getCreatedAt())
                .options(optionDtos)
                .build();
    }
}