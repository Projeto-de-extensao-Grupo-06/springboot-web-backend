package com.solarize.solarizeWebBackend.modules.budget;

import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.model.BudgetParameter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    public Budget manualBudgetCreating
            (
                    Budget budget,
                    List<BudgetParameter> bugetParameters,
                    List<Long> materials,
                    Long projectId
            )
    {
        return null;
    }
}
