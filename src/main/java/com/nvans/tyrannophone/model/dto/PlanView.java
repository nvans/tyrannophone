package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Plan;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlanView {

    private Long id;

    private String name;

    private Integer monthlyPrice;

    private String description;

    private List<OptionView> options;


    // Constructors -->
    public PlanView() {

    }

    public PlanView(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getPlanName();
        this.description = plan.getDescription();

        this.options = plan.getAvailableOptions().entrySet()
                .stream()
                .map((entry) -> new OptionView(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(OptionView::getName))
                .collect(Collectors.toList());

        this.monthlyPrice = plan.getConnectionPrice() +
                 options.stream()
                        .filter(OptionView::isConnected)
                        .mapToInt(OptionView::getPrice)
                        .sum();
    }
    // <-- Constructors

    // Getters and setters -->
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

    public Integer getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OptionView> getOptions() {
        return options;
    }

    public void setOptions(List<OptionView> options) {
        this.options = options;
    }
    // <-- Getters and setters

    // Equals and hashcode -->
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanView planView = (PlanView) o;

        if (id != null ? !id.equals(planView.id) : planView.id != null) return false;
        if (name != null ? !name.equals(planView.name) : planView.name != null) return false;
        if (monthlyPrice != null ? !monthlyPrice.equals(planView.monthlyPrice) : planView.monthlyPrice != null)
            return false;
        return description != null ? description.equals(planView.description) : planView.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (monthlyPrice != null ? monthlyPrice.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
    // <-- Equals and hashcode

    // ToString
    @Override
    public String toString() {
        return name;
    }
}
