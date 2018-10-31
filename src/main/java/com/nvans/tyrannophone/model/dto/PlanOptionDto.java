package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Option;

import java.util.List;
import java.util.stream.Collectors;

public class OptionDto {

    private Long id;

    private String name;

    private Integer price;

    private boolean isConnected;

    private List<String> incompatibleOptionsNames;


    public OptionDto() {

    }

    public OptionDto(Option option, boolean isConnected) {

        this.id = option.getId();
        this.name = option.getName();
        this.price = option.getPrice();
        this.isConnected = isConnected;

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

    public List<String> getIncompatibleOptionsNames() {
        return incompatibleOptionsNames;
    }

    public void setIncompatibleOptionsNames(List<String> incompatibleOptionsNames) {
        this.incompatibleOptionsNames = incompatibleOptionsNames;
    }
}
