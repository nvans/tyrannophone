package com.nvans.tyrannophone.utils.validation;

import com.nvans.tyrannophone.model.dao.PlanDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PlanNameValidator implements ConstraintValidator<PlanName, String> {

    private static final Logger LOGGER = Logger.getLogger(PlanNameValidator.class);

    @Autowired
    private PlanDao planDao;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        LOGGER.debug("Plan name validation");

        // when planDao is null - pre persisting validation
        if(planDao != null && planDao.findByParam("planName", value) != null) {
            return false;
        }

        return true;
    }


}
