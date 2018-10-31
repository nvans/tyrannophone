package com.nvans.tyrannophone.utils.validation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private static final Logger log = Logger.getLogger(FieldsMatchValidator.class);

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldsMatch constraint) {

        firstFieldName = constraint.firstField();
        secondFieldName = constraint.secondField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        log.info("Fields match validation");

        try {
            Object firstField = BeanUtils.getProperty(obj, firstFieldName);
            Object secondField = BeanUtils.getProperty(obj, secondFieldName);

            return firstField == null && secondField == null ||
                    firstField != null && firstField.equals(secondField);

        } catch (Exception ex) {
            log.warn("Fields match validation exception occurred " + ex.getMessage());
        }

        return true;
    }
}
