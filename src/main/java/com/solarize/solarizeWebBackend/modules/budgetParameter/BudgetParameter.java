package com.solarize.solarizeWebBackend.modules.budgetParameter;

import com.solarize.solarizeWebBackend.modules.budget.Budget;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BudgetParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parameter")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_budget", nullable = false)
    private Budget budget;
    private String name;
    private String description;
    private String metric;
    private Boolean isPreBudget;
    private Double fixedValue;
}
