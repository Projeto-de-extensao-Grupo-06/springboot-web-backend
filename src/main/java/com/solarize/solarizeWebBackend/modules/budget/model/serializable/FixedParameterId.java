package com.solarize.solarizeWebBackend.modules.budget.model.serializable;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameterTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FixedParameterId implements Serializable {
    private Long template;
    private Long budget;
}
