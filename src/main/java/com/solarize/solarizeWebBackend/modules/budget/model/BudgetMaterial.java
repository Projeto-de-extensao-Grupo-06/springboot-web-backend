package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.model.serializable.BudgetMaterialId;
import com.solarize.solarizeWebBackend.modules.materialUrl.model.MaterialUrl;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BudgetMaterialId.class)
public class BudgetMaterial {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget")
    private Budget budget;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_material_url")
    private MaterialUrl materialUrl;

    private Integer quantity;

    private Double price;
}
