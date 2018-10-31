package com.nvans.tyrannophone.model.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Component
@ApplicationScope
public class PlansCache {

    private List<PlanDto> plans;

    private boolean isValid;

    public List<PlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<PlanDto> plans) {
        this.plans = plans;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
