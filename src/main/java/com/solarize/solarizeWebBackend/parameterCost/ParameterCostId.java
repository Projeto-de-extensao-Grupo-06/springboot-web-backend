package com.solarize.solarizeWebBackend.parameterCost;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParameterCostId extends Serializable {
    private Integer parameter;
    private Integer option;
}
