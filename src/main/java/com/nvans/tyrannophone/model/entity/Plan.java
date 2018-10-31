package com.nvans.tyrannophone.model.entity;

import com.nvans.tyrannophone.utils.validation.PlanName;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Cacheable(value = false)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z][A-Za-z0-9+ ]+", message = "Incorrect plan name.")
    @PlanName
    @Column(name = "plan_name", unique = true, nullable = false, updatable = false)
    private String planName;

    @NotNull
    @PositiveOrZero(message = "Plan price should be not negative")
    @Column(name = "connection_price", nullable = false)
    private Integer connectionPrice;

    @NotNull
    @PositiveOrZero(message = "Plan price should be not negative")
    @Column(name = "monthly_price", nullable = false, updatable = false)
    private Integer monthlyPrice;

    @Column(name = "is_connection_available", nullable = false)
    private boolean isConnectionAvailable;

    @Column(name ="description", length = 55)
    private String description;

//    @NotNull
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "plan_available_option",
//            joinColumns = @JoinColumn(name = "plan_id",
//                    referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id",
//                    referencedColumnName = "id"))
//    private Set<Option> availableOptions;

//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "plan_available_option")
    @MapKeyJoinColumn(name = "option_id")
    @Column(name = "is_connected")
    private Map<Option, Boolean> availableOptions;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
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

    public boolean getConnectionAvailable() {
        return isConnectionAvailable;
    }

    public void setConnectionAvailable(boolean connectionAvailable) {
        isConnectionAvailable = connectionAvailable;
    }

//    public Set<Option> getAvailableOptions() {
//        return availableOptions;
//    }
//
//    public void setAvailableOptions(Set<Option> availableOptions) {
//        this.availableOptions = availableOptions;
//    }


    public boolean isConnectionAvailable() {
        return isConnectionAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Map<Option, Boolean> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(Map<Option, Boolean> availableOptions) {
        this.availableOptions = availableOptions;
    }

//    public Set<Option> getAvailableOptions() {
//        return availableOptions.keySet();
//    }

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
        return  Objects.equals(planName, plan.planName) &&
                Objects.equals(connectionPrice, plan.connectionPrice) &&
                Objects.equals(monthlyPrice, plan.monthlyPrice) &&
                Objects.equals(isConnectionAvailable, plan.isConnectionAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planName, connectionPrice, monthlyPrice, isConnectionAvailable);
    }

    @Override
    public String toString() {
        return planName;
    }
}
