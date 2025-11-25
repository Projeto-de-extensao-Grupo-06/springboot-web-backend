package com.solarize.solarizeWebBackend.modules.budget.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ParameterOption {
    @Id
    @JoinColumn(name = "id_parameter_option")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parameter;

    private String option;
    private Double addition_tax;

    @ManyToOne
    @JoinColumn(name = "id_budget_parameter", nullable = false)
    private BudgetParameter budgetParameter;
}
