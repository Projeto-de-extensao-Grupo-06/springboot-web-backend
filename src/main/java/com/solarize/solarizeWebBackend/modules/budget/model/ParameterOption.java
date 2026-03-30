package com.solarize.solarizeWebBackend.modules.budget.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parameter_option")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double additionTax;

    @Column(nullable = false)
    private Double fixedCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget_parameter")
    private BudgetParameter budgetParameter;
}