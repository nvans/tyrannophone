package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan_name", unique = true, nullable = false, updatable = false)
    private String planName;

    @Column(name = "connection_price", nullable = false)
    private Integer connectionPrice;

    @Column(name = "monthly_price", nullable = false, updatable = false)
    private Integer monthlyPrice;

    @Column(name = "is_connection_available", nullable = false)
    private Boolean isConnectionAvailable;

    @ManyToMany
    @JoinTable(name = "plan_available_option",
            joinColumns = @JoinColumn(name = "plan_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id",
                    referencedColumnName = "id"))
    private Set<Option> availableOptions;

    @OneToMany(mappedBy = "plan")
    private Set<Contract> contracts;


    // Getters and Setters -->

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getConnectionPrice() {
        return connectionPrice;
    }

    public void setConnectionPrice(Integer connectionPrice) {
        this.connectionPrice = connectionPrice;
    }

    public Integer getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Boolean getConnectionAvailable() {
        return isConnectionAvailable;
    }

    public void setConnectionAvailable(Boolean connectionAvailable) {
        isConnectionAvailable = connectionAvailable;
    }

    public Set<Option> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(Set<Option> availableOptions) {
        this.availableOptions = availableOptions;
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
        Plan plan = (Plan) o;
        return Objects.equals(planName, plan.planName) &&
                Objects.equals(connectionPrice, plan.connectionPrice) &&
                Objects.equals(monthlyPrice, plan.monthlyPrice) &&
                Objects.equals(isConnectionAvailable, plan.isConnectionAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planName, connectionPrice, monthlyPrice, isConnectionAvailable);
    }
}
