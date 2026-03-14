package com.solarize.solarizeWebBackend.modules.budget.helper;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetMaterial;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameter;
import com.solarize.solarizeWebBackend.modules.budget.model.PersonalizedParameter;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetCalcs {

    public static Map<String, Double> budgetTotalCost(Budget budget) {

        if (budget == null) {
            return Map.of(
                    "subtotal", 0.0,
                    "totalCost", 0.0
            );
        }

        List<FixedParameter> fixedParams = budget.getFixedParameters() != null
                ? budget.getFixedParameters()
                : Collections.emptyList();

        List<PersonalizedParameter> personalizedParams = budget.getPersonalizedParameters() != null
                ? budget.getPersonalizedParameters()
                : Collections.emptyList();

        List<BudgetMaterial> materials = budget.getMaterials() != null
                ? budget.getMaterials()
                : Collections.emptyList();

        double totalAmountFixedParams = fixedParams.stream()
                .filter(p ->
                        p.getTemplate() != null &&
                                p.getTemplate().getType() == ParameterValueType.AMOUNT
                )
                .mapToDouble(p -> safeDouble(p.getParameterValue()))
                .sum();

        double totalAmountPersonalizedParams = personalizedParams.stream()
                .filter(p -> p.getType() == ParameterValueType.AMOUNT)
                .mapToDouble(p -> safeDouble(p.getParameterValue()))
                .sum();

        double totalPercentFixedParams = fixedParams.stream()
                .filter(p ->
                        p.getTemplate() != null &&
                                p.getTemplate().getType() == ParameterValueType.PERCENT
                )
                .mapToDouble(p -> safeDouble(p.getParameterValue()))
                .sum();

        double totalPercentPersonalizedParams = personalizedParams.stream()
                .filter(p -> p.getType() == ParameterValueType.PERCENT)
                .mapToDouble(p -> safeDouble(p.getParameterValue()))
                .sum();

        double totalMaterialCost = materials.stream()
                .mapToDouble(m ->
                        safeDouble(m.getPrice()) *
                                safeDouble(Double.valueOf(m.getQuantity()))
                )
                .sum();

        double subtotal =
                totalAmountFixedParams +
                        totalAmountPersonalizedParams +
                        totalMaterialCost;

        double totalPercent =
                totalPercentFixedParams +
                        totalPercentPersonalizedParams;

        subtotal = subtotal * (1 + totalPercent / 100);


        DiscountType discountType =
                budget.getDiscountType() != null
                        ? budget.getDiscountType()
                        : DiscountType.AMOUNT;

        double discount =
                budget.getDiscount() != null
                        ? budget.getDiscount()
                        : 0.0;

        double totalCost = subtotal;

        switch (discountType) {

            case PERCENT -> {

                if (discount > 100) {
                    throw new BadRequestException("Discount percent cannot be greater than 100.");
                }

                totalCost = subtotal * (1 - discount / 100);
            }

            case AMOUNT -> {

                if (discount > subtotal) {
                    throw new BadRequestException("The discount cannot be greater than the subtotal.");
                }

                totalCost = subtotal - discount;
            }

            case MOCK_TOTAL -> {

                if (discount > subtotal) {
                    throw new BadRequestException("The discount cannot be greater than the subtotal.");
                }

                totalCost = discount;
            }
        }

        return Map.of(
                "subtotal", subtotal,
                "totalCost", totalCost
        );
    }

    private static double safeDouble(Double value) {
        return value != null ? value : 0.0;
    }

    public static Map<String, Double> preBudgetCalc(
            Double monthlyBill,
            Double tariff,
            Double sunHours,
            Double systemEfficiency,
            Double pricePerKwp
    ) {
        Map<String, Double> result = new HashMap<>();

        double bill = monthlyBill != null ? monthlyBill : 0.0;
        final int MONTHS_PER_YEAR = 12;
        final int DAYS = 30;

        double consumption = bill > 0 ? bill / tariff : 0.0;

        double kwp = (bill > 0)
                ? (consumption / (DAYS * sunHours * systemEfficiency))
                : 0.0;

        double generation = kwp * sunHours * DAYS * systemEfficiency;

        double savings = generation * tariff;

        double cost = kwp * pricePerKwp;

        double paybackYears = (savings > 0)
                ? cost / (savings * MONTHS_PER_YEAR)
                : 0.0;

        result.put("bill", bill);
        result.put("consumption", consumption);
        result.put("kwp", kwp);
        result.put("generation", generation);
        result.put("savings", savings);
        result.put("cost", cost);
        result.put("paybackYears", paybackYears);

        return result;
    }
}