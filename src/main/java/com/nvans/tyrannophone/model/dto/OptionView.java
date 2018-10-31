package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Option;

public class OptionView {

    private String name;
    private Integer price;
    private boolean isConnected;

    // Constructors -->
    public OptionView() {

    }

    public OptionView(Option option) {
        this(option, false);
    }

    public OptionView(Option option, boolean isConnected) {

        this.name = option.getName();
        this.price = option.getPrice();
        this.isConnected = isConnected;
    }
    // <-- Constructors

    // Getters and setters -->
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
    // <-- Getters and setters

    // Equals and hashcode -->
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionView that = (OptionView) o;

        if (isConnected != that.isConnected) return false;
        if (!name.equals(that.name)) return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (isConnected ? 1 : 0);
        return result;
    }
    // <-- Equals and hashcode

    // ToString
    @Override
    public String toString() {
        return name;
    }
}
