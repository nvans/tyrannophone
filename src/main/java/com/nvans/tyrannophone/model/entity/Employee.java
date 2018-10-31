package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends Details {

    private static final long serialVersionUID = 1L;

    @Column(name = "position", nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_contract",
            joinColumns = {@JoinColumn(name = "employee_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "contract_number",
                    referencedColumnName = "contract_number",
                    unique = true)})
    private Set<Contract> contracts = new HashSet<>();


    // Getters and Setters -->

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
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

        Employee employee = (Employee) o;

        if(!getFirstName().equals(employee.getFirstName())) return false;
        if(!getLastName().equals(employee.getLastName())) return false;
        if(!getPassport().equals(employee.getPassport())) return false;
        if(!getAddress().equals(employee.getAddress())) return false;
        if(!getUser().equals(employee.getUser())) return false;
        if (!position.equals(employee.position)) return false;
        return manager != null ? manager.equals(employee.manager) : employee.manager == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getPassport().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getUser().hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        return result;
    }
}
