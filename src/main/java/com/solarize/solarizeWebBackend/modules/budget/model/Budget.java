package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.DiscountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;

    private Boolean finalBudget;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetMaterial> materials = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedParameter> fixedParameters = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalizedParameter> personalizedParameters = new ArrayList<>();
}
