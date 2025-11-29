package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonalizedParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personalized_parameter")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ParameterValueType type;

    @Column(nullable = false)
    private Double parameterValue;

    @ManyToOne(fetch = FetchType.LAZY)
    private Budget budget;
}
