package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Contract;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long contractNumber;

    private PlanDto plan;

    private List<PlanOptionDto> options;

    private boolean isActive;

    public ContractDto() {

    }

    public ContractDto(Contract contract) {

        this.contractNumber = contract.getContractNumber();
        this.plan = new PlanDto(contract.getPlan());
        this.options = contract.getOptions()
                .stream().map(option -> new PlanOptionDto(option, true)).collect(Collectors.toList());

        options.addAll(contract.getPlan().getAvailableOptions().entrySet().stream()
                .filter(Map.Entry::getValue) // keep connected
                .map(entry -> new PlanOptionDto(entry.getKey(), true)) // convert
                .collect(Collectors.toList()));

        this.isActive = contract.isActive();
    }


    public Integer getPrice() {

        return plan.getConnectionPrice() + options.stream().mapToInt(PlanOptionDto::getPrice).sum();
    }


    public Long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<PlanOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<PlanOptionDto> options) {
        this.options = options;
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return contractNumber.toString();
    }
}
