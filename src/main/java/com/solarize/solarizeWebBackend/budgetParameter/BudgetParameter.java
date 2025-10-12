package com.solarize.solarizeWebBackend.budgetParameter;

import com.solarize.solarizeWebBackend.budget.Budget;
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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget", nullable = false)
    private Budget budget;

    private String name;
    private String description;
    private String metric;
    private Boolean isPreBudget;
    private Double fixedValue;
}
