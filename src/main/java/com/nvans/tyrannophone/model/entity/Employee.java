package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends UserDetails {

    private static final long serialVersionUID = 1L;

    @Column(name = "position", nullable = false)
    private String position;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;


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
