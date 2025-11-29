package com.solarize.solarizeWebBackend.modules.budget.model.serializable;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.FixedParameterTemplate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FixedParameterId implements Serializable {
    private FixedParameterTemplate template;
    private Budget budget;
}
