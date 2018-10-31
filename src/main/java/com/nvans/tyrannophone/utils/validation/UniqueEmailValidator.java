package com.nvans.tyrannophone.utils.validation;

import com.nvans.tyrannophone.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private static final Logger log = Logger.getLogger(UniqueEmailValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Email uniqueness validation for email: " + value);

        if (userService.getUserByEmail(value) != null) {
            return false;
        }

        return true;
    }
}
