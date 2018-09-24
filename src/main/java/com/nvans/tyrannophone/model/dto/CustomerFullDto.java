package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Customer;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerFullDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String passport;

    private String address;

    private boolean isActive;

    private Integer balance;

    private Set<ContractDto> contracts;

    public CustomerFullDto(Customer customer) {

        this.id = customer.getId();

        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getUser().getEmail();
        this.passport = customer.getPassport();
        this.address = customer.getAddress();
        this.isActive = customer.getUser().isActive();
        this.balance = customer.getBalance();

        this.contracts = customer.getContracts().stream()
                .map(contract -> new ContractDto(contract, false))
                .collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Set<ContractDto> getContracts() {
        return contracts;
    }

    public void setContracts(Set<ContractDto> contracts) {
        this.contracts = contracts;
    }
}
