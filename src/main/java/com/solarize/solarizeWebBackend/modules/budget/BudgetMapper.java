package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.request.*;
import com.solarize.solarizeWebBackend.modules.budget.dto.response.BudgetResponseDto;
import com.solarize.solarizeWebBackend.modules.budget.model.*;
import com.solarize.solarizeWebBackend.modules.materialUrl.model.MaterialUrl;

import java.util.List;

public class BudgetMapper {
    public static Budget toEntity(BudgetManualCreateDto dto) {
        if(dto == null) return null;

        List<FixedParameter> fixedParameters = dto.getFixedParameters().stream().map(p ->
           FixedParameter.builder()
                   .parameterValue(p.getValue())
                   .template(FixedParameterTemplate.builder().uniqueName(p.getParameterName()).build())
                   .build()
        ).toList();

        List<PersonalizedParameter> personalizedParameters = dto.getPersonalizedParameters().stream().map(p ->
            PersonalizedParameter.builder()
                    .name(p.getName())
                    .parameterValue(p.getValue())
                    .type(p.getType())
                    .build()
        ).toList();

        List<BudgetMaterial> budgetMaterials = dto.getMaterials().stream()
                .map(m -> {
                    MaterialUrl materialUrl = new MaterialUrl();
                    materialUrl.setId(m.getMaterialId());

                    return BudgetMaterial.builder()
                            .materialUrl(materialUrl)
                            .quantity(m.getQuantity())
                            .build();
                }
        ).toList();

        return Budget.builder()
                .discount(dto.getDiscount())
                .discountType(dto.getDiscountType())
                .finalBudget(dto.getFinalBudget())
                .fixedParameters(fixedParameters)
                .personalizedParameters(personalizedParameters)
                .materials(budgetMaterials)
                .build();
    }

    public static Budget toEntity(BudgetPatchDto dto) {
        return Budget.builder()
                .discount(dto.getDiscount())
                .discountType(dto.getDiscountType())
                .finalBudget(dto.getFinalBudget())
                .build();
    }

    public static BudgetResponseDto toDto(Budget budget) {
        List<BudgetResponseDto.FixedParameterResponseDto> fixedParameters = budget.getFixedParameters().stream()
                .map(p ->
                        BudgetResponseDto.FixedParameterResponseDto.builder()
                                .parameterName(p.getTemplate().getUniqueName())
                                .valueType(p.getTemplate().getType())
                                .value(p.getParameterValue())
                                .build()
                ).toList();

        List<BudgetResponseDto.PersonalizedParameterResponseDto> personalizedParameters = budget.getPersonalizedParameters().stream()
                .map(p ->
                        BudgetResponseDto.PersonalizedParameterResponseDto.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .type(p.getType())
                                .value(p.getParameterValue())
                                .build()
                        ).toList();

        List<BudgetResponseDto.BudgetMaterialResponseDto> materialsUrl = budget.getMaterials().stream()
                .map(m ->
                        BudgetResponseDto.BudgetMaterialResponseDto.builder()
                                .materialUrlId(m.getMaterialUrl().getId())
                                .name(m.getMaterialUrl().getMaterial().getName())
                                .url(m.getMaterialUrl().getUrl())
                                .unitPrice(m.getPrice())
                                .quantity(m.getQuantity())
                                .build()
                ).toList();

        return BudgetResponseDto.builder()
                .id(budget.getId())
                .totalCost(budget.getTotalCost())
                .subtotal(budget.getSubtotal())
                .discount(budget.getDiscount())
                .discountType(budget.getDiscountType())
                .finalBudget(budget.getFinalBudget())
                .materials(materialsUrl)
                .fixedParameters(fixedParameters)
                .personalizedParameters(personalizedParameters)
                .build();
    }

    public static List<BudgetMaterial> toEntity(UpdateMaterialDto dto) {
        return dto.getMaterials().stream().map(d -> {
            MaterialUrl materialUrl = new MaterialUrl();
            materialUrl.setId(d.getMaterialId());

            return BudgetMaterial.builder()
                    .quantity(d.getQuantity())
                    .materialUrl(materialUrl)
                    .build();
        }).toList();
    }

    public static List<FixedParameter> toEntity(UpdateFixedParametersDto dto) {
        return dto.getFixedParameters().stream().map(d -> {
            FixedParameterTemplate template = new FixedParameterTemplate();
            FixedParameter fixedParameter = new FixedParameter();

            template.setUniqueName(d.getParameterName());
            fixedParameter.setTemplate(template);
            fixedParameter.setParameterValue(d.getValue());

            return fixedParameter;
        }).toList();
    }

    public static List<PersonalizedParameter> toEntity(UpdatePersonalizedParametersDto dto) {
        return dto.getPersonalizedParameters().stream().map(d -> {
            PersonalizedParameter personalizedParameter = new PersonalizedParameter();

            personalizedParameter.setId(d.getId());
            personalizedParameter.setName(d.getName());
            personalizedParameter.setParameterValue(d.getValue());
            personalizedParameter.setType(d.getType());

            return personalizedParameter;
        }).toList();
    }
}
