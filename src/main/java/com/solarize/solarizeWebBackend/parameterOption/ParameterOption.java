package com.solarize.solarizeWebBackend.parameterOption;

import com.solarize.solarizeWebBackend.budgetParameter.BudgetParameter;
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
    private Integer id;

    private String type;
    private Double additionTax;
    private Double fixedCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parameter")
    private BudgetParameter parameter;
}
