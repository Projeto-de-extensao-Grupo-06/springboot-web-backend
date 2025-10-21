package com.solarize.solarizeWebBackend.modules.budget;

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

    private Double totalCost;
    private Double discount;
    private Double materialCost;
    private Double serviceCost;
}
