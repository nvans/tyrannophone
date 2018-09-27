package com.nvans.tyrannophone.utils.validation;

import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ContractNumberValidator implements ConstraintValidator<ContractNumber, Long> {

    private static final Logger logger = Logger.getLogger(ContractNumberValidator.class);

    public boolean isValid(Long number, ConstraintValidatorContext context) {

        logger.debug("Contract number validation...");

        if (number == null) return false;

        return Pattern.matches("7000\\d{5}", number.toString());
    }
}
