package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends Details {

    private static final long serialVersionUID = 1L;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
//    @JoinTable(name = "customer_contract",
//               joinColumns = {@JoinColumn(name = "customer_id",
//                                          referencedColumnName = "id")},
//               inverseJoinColumns = {@JoinColumn(name = "contract_number",
//                                                 referencedColumnName = "contract_number",
//                                                 unique = true)})
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
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if(!getFirstName().equals(customer.getFirstName())) return false;
        if(!getLastName().equals(customer.getLastName())) return false;
        if(!getPassport().equals(customer.getPassport())) return false;
        if(!getAddress().equals(customer.getAddress())) return false;
        if(!getUser().equals(customer.getUser())) return false;
        if (!balance.equals(customer.balance)) return false;
        return contracts != null ? contracts.equals(customer.contracts) : customer.contracts == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getPassport().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getUser().hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + (contracts != null ? contracts.hashCode() : 0);
        return result;
    }
}
