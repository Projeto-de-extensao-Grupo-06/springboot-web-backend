package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.TaxType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_budget")
    private Long id;

    private Double subtotal;
    private Double totalCost;
    private Double discount;

    private Double serviceCost;
    private Double materialCost;
    private Boolean finalBudget;
}
