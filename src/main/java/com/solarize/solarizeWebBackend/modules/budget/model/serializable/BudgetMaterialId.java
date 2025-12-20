package com.solarize.solarizeWebBackend.modules.budget.model.serializable;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BudgetMaterialId implements Serializable {
    private Long budget;
    private Long materialUrl;
}
