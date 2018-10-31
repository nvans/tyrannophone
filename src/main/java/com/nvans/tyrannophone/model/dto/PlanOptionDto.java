package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Option;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents option that connected to the plan.
 *
 * @author Ivan Konovalov
 */
public class PlanOptionDto {

    private Long id;

    private String name;

    private Integer price;

    private boolean isConnected;

    private String parentOption;

    private List<String> incompatibleOptionsNames;


    public PlanOptionDto() {

    }

    public PlanOptionDto(Option option) {
        this(option, false);
    }

    public PlanOptionDto(Option option, boolean isConnected) {

        this.id = option.getId();
        this.name = option.getName();
        this.price = option.getPrice();
        this.isConnected = isConnected;

        Option parent = option.getParentOption();

        this.parentOption = parent != null ? parent.getName() : "";

        this.incompatibleOptionsNames = option.getIncompatibleOptions()
                .stream().map(Option::getName).collect(Collectors.toList());

    }

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

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getParentOption() {
        return parentOption;
    }

    public void setParentOption(String parentOption) {
        this.parentOption = parentOption;
    }

    public List<String> getIncompatibleOptionsNames() {
        return incompatibleOptionsNames;
    }

    public void setIncompatibleOptionsNames(List<String> incompatibleOptionsNames) {
        this.incompatibleOptionsNames = incompatibleOptionsNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanOptionDto that = (PlanOptionDto) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
