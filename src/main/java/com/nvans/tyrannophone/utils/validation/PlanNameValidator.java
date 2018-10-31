package com.nvans.tyrannophone.utils.validation;

import com.nvans.tyrannophone.service.PlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PlanNameValidator implements ConstraintValidator<PlanName, String> {

    private static final Logger log = Logger.getLogger(PlanNameValidator.class);

    @Autowired
    private PlanService planService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Plan name validation: " + value);

        // when planService is null - pre persisting validation
        if(planService != null && planService.getPlan(value) != null) {
            return false;
        }

        return true;
    }


}
