package com.solarize.solarizeWebBackend.associationEntities.parameterCost;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParameterCostId implements Serializable {
    private Long parameter;
    private Long option;
}
