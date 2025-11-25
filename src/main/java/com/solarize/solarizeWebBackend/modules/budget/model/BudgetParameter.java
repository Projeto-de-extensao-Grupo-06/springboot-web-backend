package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
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

    @Enumerated(EnumType.STRING)
    private TaxType taxType;
    private Double additionTax;
}
