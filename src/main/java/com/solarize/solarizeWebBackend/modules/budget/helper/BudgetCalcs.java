package com.solarize.solarizeWebBackend.modules.budget.helper;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.PersonalizedParameter;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;

import java.util.Map;

public class BudgetCalcs {
    public static Map<String, Double> budgetTotalCost(Budget budget) {
        double subtotal = 0.0;
        double totalCost = 0.0;

        Double totalAmountFixedParams = budget.getFixedParameters().stream()
                .filter(p -> p.getTemplate().getType() == ParameterValueType.AMOUNT)
                .mapToDouble(FixedParameter::getParameterValue)
                .sum();

        Double totalAmountPersonalizedParams = budget.getPersonalizedParameters().stream()
                .filter(p -> p.getType() == ParameterValueType.AMOUNT)
                .mapToDouble(PersonalizedParameter::getParameterValue)
                .sum();


        Double totalPercentFixedParams = budget.getFixedParameters().stream()
                .filter(p -> p.getTemplate().getType() == ParameterValueType.PERCENT)
                .mapToDouble(FixedParameter::getParameterValue)
                .sum();

        Double totalPercentPersonalizedParams = budget.getPersonalizedParameters().stream()
                .filter(p -> p.getType() == ParameterValueType.PERCENT)
                .mapToDouble(PersonalizedParameter::getParameterValue)
                .sum();


        Double totalMaterialCost = budget.getMaterials().stream()
                .mapToDouble(m -> m.getPrice() * m.getQuantity())
                .sum();


        subtotal = totalAmountFixedParams + totalAmountPersonalizedParams + totalMaterialCost;
        double totalPercent = totalPercentFixedParams + totalPercentPersonalizedParams;

        subtotal = subtotal * (1 + totalPercent / 100);

        switch (budget.getDiscountType()) {
            case PERCENT -> {
                if(budget.getDiscount() > 100) {
                    throw new BadRequestException("The discount cannot be greater than the subtotal.");
                }

                totalCost = subtotal * (1 - budget.getDiscount() / 100);
            }
            case AMOUNT -> {
                if(budget.getDiscount() > subtotal) {
                    throw new BadRequestException("The discount cannot be greater than the subtotal.");
                }

                totalCost = subtotal - budget.getDiscount();
            }
            case MOCK_TOTAL -> {
                if(budget.getDiscount() > subtotal) {
                    throw new BadRequestException("The discount cannot be greater than the subtotal.");
                }

                totalCost = budget.getDiscount();
            }
        }

        return Map.of(
                "subtotal", subtotal,
                "totalCost", totalCost
        );
    }
}
