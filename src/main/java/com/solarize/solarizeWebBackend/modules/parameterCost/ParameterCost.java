package com.solarize.solarizeWebBackend.modules.parameterCost;

import com.solarize.solarizeWebBackend.modules.budgetParameter.BudgetParameter;
import com.solarize.solarizeWebBackend.modules.parameterOption.ParameterOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ParameterCostId.class)
public class ParameterCost {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parameter")
    private BudgetParameter parameter;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_option")
    private ParameterOption option;

    private Double cost;
}
