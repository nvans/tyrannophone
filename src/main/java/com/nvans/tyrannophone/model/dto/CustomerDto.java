package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.User;

import java.io.Serializable;

public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName = "Your first name";

    private String lastName = "Your last name";

    private String email = "Your e-mail";

    private String address = "Your address";

    private String passport = "Your passport number";

    private String password = "Your password";

    private String newPassword = "New password";

    private String newPasswordConfirmation = "Confirm password";

    public CustomerDto() {

    }

    public CustomerDto(User user) {

        Customer customer = (Customer) user.getDetails();

        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = user.getEmail();
        this.address = customer.getAddress();
        this.passport = customer.getPassport();

    }

    public CustomerDto(Customer customer) {

        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getUser().getEmail();
        this.address = customer.getAddress();
        this.passport = customer.getPassport();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
