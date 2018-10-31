package com.nvans.tyrannophone.model.dto;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.utils.validation.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CustomerRegistrationDto {

    @NotEmpty(message = "Email must be not empty")
    @Email(message = "Incorrect email address")
    @UniqueEmail
    private String email;

    private String confirmEmail;

    @Pattern(regexp = "[A-Z][a-z]{1,20}",
             message = "Must contain not more than 20 english letters. First letter must be upper cased")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]{1,20}",
             message = "Must contain not more than 20 english letters. First letter must be upper cased")
    private String lastName;

    @Pattern(regexp = "[A-Z]{3} [0-9]{6}",
             message = "Must contain 3 upper cased letter and 6 numbers separated with space")
    private String passport;

    @NotEmpty(message = "Address must be not empty.")
    private String address;

    @NotEmpty(message = "Password must be not empty.")
    private String password;

    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
