package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @Column(name = "connection_price")
    private Integer connectionPrice;

    @Column(name = "monthly_price")
    private Integer monthlyPrice;

    @Column(name = "is_available")
    private boolean isConnectionAvailable;

    @OneToMany
    @JoinTable(name = "incompatible_options",
        joinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "inc_option", referencedColumnName = "id"))
    private Set<Option> incompatibleOptions;

    // Constructor
    public Option() {
        incompatibleOptions = new HashSet<>();
    }

    // Getters and Setters -->

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isConnectionAvailable() {
        return isConnectionAvailable;
    }

    public void setConnectionAvailable(boolean connectionAvailable) {
        isConnectionAvailable = connectionAvailable;
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }


    // <-- Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (isConnectionAvailable != option.isConnectionAvailable) return false;
        if (!name.equals(option.name)) return false;
        if (!connectionPrice.equals(option.connectionPrice)) return false;
        return monthlyPrice.equals(option.monthlyPrice);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + connectionPrice.hashCode();
        result = 31 * result + monthlyPrice.hashCode();
        result = 31 * result + (isConnectionAvailable ? 1 : 0);
        return result;
    }
}
