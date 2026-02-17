package com.solarize.solarizeWebBackend.shared.event;

public record BudgetCreateEvent(Long projectId, Boolean finalBudget) { }
