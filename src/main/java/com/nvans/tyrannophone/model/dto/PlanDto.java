package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Representation of the plan.
 *
 */
public class PlanDto implements Serializable {

    private Long id;

    private String planName;

    private Integer connectionPrice;

    private Integer monthlyPrice;

    private boolean isConnectionAvailable;

    private String description;

    private List<PlanOptionDto> availableOptions;

    private List<PlanOptionDto> connectedOptions;


    public PlanDto() {

    }

    public PlanDto(Plan plan) {

        this.id = plan.getId();
        this.planName = plan.getPlanName();
        this.connectionPrice = plan.getConnectionPrice();
        this.monthlyPrice = plan.getMonthlyPrice();
        this.isConnectionAvailable = plan.isConnectionAvailable();
        this.description = plan.getDescription();

        this.availableOptions = plan.getAvailableOptions().entrySet()
                .stream()
                .filter(entry -> entry.getKey().isConnectionAvailable())
                .map(entry -> new PlanOptionDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(PlanOptionDto::getName))
                .collect(Collectors.toList());

        this.connectedOptions =
                plan.getAvailableOptions().entrySet()
                        .stream()
                        .filter(Map.Entry::getValue)
                        .map(Map.Entry::getKey)
                        .map(option -> new PlanOptionDto(option, true))
                        .sorted(Comparator.comparing(PlanOptionDto::getName))
                        .collect(Collectors.toList());

        this.monthlyPrice = plan.getConnectionPrice() +
                connectedOptions.stream().mapToInt(PlanOptionDto::getPrice).sum();

    }

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

    public boolean isConnectionAvailable() {
        return isConnectionAvailable;
    }

    public void setConnectionAvailable(boolean connectionAvailable) {
        isConnectionAvailable = connectionAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlanOptionDto> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(List<PlanOptionDto> availableOptions) {
        this.availableOptions = availableOptions;
    }

    public List<PlanOptionDto> getConnectedOptions() {
        return connectedOptions;
    }

    public void setConnectedOptions(List<PlanOptionDto> connectedOptions) {
        this.connectedOptions = connectedOptions;
    }

    @Override
    public String toString() {
        return planName;
    }
}
