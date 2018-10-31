package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String passport;

    private List<Long> contracts;

    private boolean isActive;

    public CustomerDto() {

    }

    public CustomerDto(Customer customer) {

        this.customerId = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getUser().getEmail();
        this.address = customer.getAddress();
        this.passport = customer.getPassport();
        this.isActive = customer.getUser().isActive();

        this.contracts = new ArrayList<>();

        for (Contract contract : customer.getContracts()) {
            this.contracts.add(contract.getContractNumber());
        }
        this.contracts.sort(Comparator.naturalOrder());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public List<Long> getContracts() {
        return contracts;
    }

    public void setContracts(List<Long> contracts) {
        this.contracts = contracts;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
