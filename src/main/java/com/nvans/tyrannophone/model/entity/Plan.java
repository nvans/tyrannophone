package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Plan.findAll";
    public static final String FIND_BY_NAME = "Plan.findByName";

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "plan_option",
            joinColumns = @JoinColumn(name = "plan_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id",
                    referencedColumnName = "id")
    )
    private Set<Option> availableOptions;

    // Constructor

    public Plan() {
        availableOptions = new HashSet<>();
    }


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


    // <-- Getters and Setters
}
