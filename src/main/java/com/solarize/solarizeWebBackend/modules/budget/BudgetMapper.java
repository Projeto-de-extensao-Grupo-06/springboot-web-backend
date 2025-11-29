package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.dto.BudgetManualCreateDto;
import com.solarize.solarizeWebBackend.modules.budget.model.*;
import com.solarize.solarizeWebBackend.modules.material.model.Material;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;

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
}
