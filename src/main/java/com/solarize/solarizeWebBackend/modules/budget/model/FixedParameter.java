package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.model.serializable.FixedParameterId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(FixedParameterId.class)
public class FixedParameter {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget")
    private Budget budget;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_template")
    private FixedParameterTemplate template;

    @Column(unique = true)
    private Double parameterValue;
}
