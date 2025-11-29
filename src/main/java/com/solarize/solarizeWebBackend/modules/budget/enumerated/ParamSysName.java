package com.solarize.solarizeWebBackend.modules.budget.enumerated;

import lombok.Getter;

@Getter
public enum ParamSysName {
    TRANSPORT(false),
    FOOD(false),
    MATERIAL_VALUE(false),
    WORK_VALUE(false),
    ESTIMATED_MATERIAL_VALUE(true),
    ESTIMATED_WORK_VALUE(true),
    ENERGY_BILL_VALUE(true);

    private final Boolean justPreBudget;

    ParamSysName(Boolean justPreBudget) {
        this.justPreBudget = justPreBudget;
    }
}
