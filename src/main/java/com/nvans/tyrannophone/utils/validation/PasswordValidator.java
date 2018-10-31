package com.nvans.tyrannophone.utils.validation;

import com.nvans.tyrannophone.model.dto.CustomerRegistrationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerRegistrationDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerRegistrationDto registrationDto = (CustomerRegistrationDto) target;

        if(!(registrationDto.getPassword().equals(registrationDto.getConfirmPassword()))) {
            errors.rejectValue("password", "password and confirm password are not match");
        }
    }
}
