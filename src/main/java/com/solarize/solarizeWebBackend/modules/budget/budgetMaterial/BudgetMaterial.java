package com.solarize.solarizeWebBackend.modules.budget.budgetMaterial;

import com.solarize.solarizeWebBackend.modules.budget.core.Budget;
import com.solarize.solarizeWebBackend.modules.materialCatalog.MaterialCatalog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(BudgetMaterialId.class)
public class BudgetMaterial {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget")
    private Budget budget;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_material")
    private MaterialCatalog material;

    private Double quantity;

    private Double price;
}
