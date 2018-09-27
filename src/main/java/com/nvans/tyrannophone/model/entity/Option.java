package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "options")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[A-Z][A-Za-z0-9()+ ]{2,}", message = "Invalid option name.")
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @PositiveOrZero
    @Column(name = "price")
    private Integer price;

    @Column(name = "is_available")
    private boolean isConnectionAvailable;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "option_incompatible_option",
        joinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "inc_option_id", referencedColumnName = "id"))
    private Set<Option> incompatibleOptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "option_dependencies",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_option_id"))
    private Option parentOption;

    @OneToMany(mappedBy = "parentOption", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Option> childOptions = new HashSet<>();

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Option getParentOption() {
        return parentOption;
    }

    public void setParentOption(Option parentOption) {
        this.parentOption = parentOption;

        if (parentOption != null) {
            parentOption.getChildOptions().add(this);
        }
    }

    public Set<Option> getChildOptions() {
        return childOptions;
    }

    public void setChildOptions(Set<Option> childOptions) {
        this.childOptions = childOptions;
    }

    // <-- Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;

        return isConnectionAvailable == option.isConnectionAvailable &&
                Objects.equals(name, option.name) &&
                Objects.equals(price, option.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, isConnectionAvailable);
    }

    @Override
    public String toString() {
        return name;
    }
}
