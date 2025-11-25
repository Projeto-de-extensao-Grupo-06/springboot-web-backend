package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetMaterial;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetParameterRepository;
import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetRepository;
import com.solarize.solarizeWebBackend.modules.material.MaterialService;
import com.solarize.solarizeWebBackend.modules.material.model.MaterialUrl;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetParameterRepository budgetParameterRepository;
    private final MaterialService materialService;

    public Budget manualBudgetCreating
            (
                    Budget budget,
                    TaxType discountType,
                    List<BudgetParameter> budgetParameters,
                    List<BudgetMaterial> materialsId,
                    Long projectId
            )
    {

        double additionalPercent = 0.0;
        double materialCost;

        double subtotal = 0.0;
        double discount;
        double totalCost;

        for(BudgetParameter b : budgetParameters) {
            if(b.getTaxType() == TaxType.PERCENT) {
                additionalPercent += b.getAdditionTax();
            }
            else {
                subtotal += b.getAdditionTax();
            }
        }

        boolean allMaterialsUrlExists = materialService.allMaterialsUrlExists(materialsId);

        if(!allMaterialsUrlExists) {
            throw new NotFoundException("One of the material URL IDs does not exist.");
        }

        List<MaterialUrl> materials = materialService.findMaterialByIdIn(materialsId);
        materialCost = materials.stream().mapToDouble(MaterialUrl::getPrice).sum();

        subtotal += materialCost;
        subtotal += subtotal * (additionalPercent / 100);

        totalCost = subtotal;

        if(budget.getDiscount() != null) {
            if(discountType == TaxType.PERCENT) {
                discount = totalCost * (budget.getDiscount() / 100);
            } else {
                discount = budget.getDiscount();
            }

            totalCost -= discount;
            budget.setDiscount(discount);
        }

        budget.setMaterialCost(materialCost);
        budget.setSubtotal(subtotal);
        budget.setTotalCost(totalCost);

        Budget savedBudget = budgetRepository.save(budget);


        List<BudgetParameter> savedBudgetParameters = budgetParameters.stream()
                .map(parameter -> {
                    parameter.setId(savedBudget.getId());
                    return budgetParameterRepository.save(parameter);
                })
                .toList();


        return budget;
    }
}
