package com.solarize.solarizeWebBackend.modules.budget.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_budget_parameter")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String metric;

    @Column(nullable = false)
    private Boolean isPreBudget;

    @Column(nullable = false)
    private Double fixedValue;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}