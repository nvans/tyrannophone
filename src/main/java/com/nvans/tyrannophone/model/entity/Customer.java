package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends Details {

    private static final long serialVersionUID = 1L;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Contract> contracts = new HashSet<>();

    // Getters and Setters -->

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    // <-- Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(balance, customer.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), balance);
    }

    @Override
    public String toString() {
        return this.getUser().getEmail();
    }
}
