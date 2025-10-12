package com.solarize.solarizeWebBackend.budgetMaterial;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BudgetMaterialId implements Serializable {
    private Integer budget;
    private Integer material;
}
