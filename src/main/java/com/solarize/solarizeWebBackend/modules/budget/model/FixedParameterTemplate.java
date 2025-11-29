package com.solarize.solarizeWebBackend.modules.budget.model;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixedParameterTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fixed_param")
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FixedParameterName uniqueName;

    @Column(nullable = false)
    private ParameterValueType type;
}
