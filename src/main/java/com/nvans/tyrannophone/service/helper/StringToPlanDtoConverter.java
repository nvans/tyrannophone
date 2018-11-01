package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPlanDtoConverter implements Converter<String, PlanDto> {

    @Autowired
    private PlanService planService;


    @Override
    public PlanDto convert(String planName) {
        return planService.getPlanDto(planName);
    }
}
