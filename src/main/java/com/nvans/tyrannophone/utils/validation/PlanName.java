package com.nvans.tyrannophone.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PlanNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanName {

    String message() default "Plan with this name already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
