package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPlanConverter implements Converter<String, Plan> {

    @Autowired
    PlanService planService;

    @Override
    public Plan convert(String planName) {

        return planService.getPlan(planName);
    }
}
