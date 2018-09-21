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

    @Column(name = "price")
    private Integer price;

    @Column(name = "is_available")
    private boolean isConnectionAvailable;

    @OneToMany
    @JoinTable(name = "option_incompatible_option",
        joinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "inc_option_id", referencedColumnName = "id"))
    private Set<Option> incompatibleOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "option_dependencies",
            joinColumns = @JoinColumn(name = "parent_option_id"),
            inverseJoinColumns = @JoinColumn(name = "child_option_id"))
    private Option parentOption;

    @OneToMany(mappedBy = "parentOption")
    private Set<Option> childOptions;

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

        if (isConnectionAvailable != option.isConnectionAvailable) return false;
        if (!name.equals(option.name)) return false;
        return price.equals(option.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (isConnectionAvailable ? 1 : 0);
        return result;
    }
}
