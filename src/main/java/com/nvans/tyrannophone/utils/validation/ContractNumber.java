package com.nvans.tyrannophone.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContractNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContractNumber {

    String message() default "Contract number should be 7000XXXXXXX";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
