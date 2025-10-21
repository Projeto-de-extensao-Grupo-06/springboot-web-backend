package com.solarize.solarizeWebBackend.modules.parameterOption;

import com.solarize.solarizeWebBackend.modules.budgetParameter.BudgetParameter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ParameterOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_option")
    private Long id;

    private String type;
    private Double additionTax;
    private Double fixedCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parameter")
    private BudgetParameter parameter;
}
