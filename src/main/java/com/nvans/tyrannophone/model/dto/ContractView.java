package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Contract;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContractView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long contractNumber;

    private String planName;

    private String customerFullName;

    private String customerEmail;

    private List<OptionView> options;

    private Integer monthlyPrice;

    private boolean isActive;

    public ContractView() {
    }

    public ContractView(Contract contract) {

        this.contractNumber = contract.getContractNumber();
        this.planName = contract.getPlan().getPlanName();

        this.customerFullName = contract.getCustomer().getFirstName() +
                " " + contract.getCustomer().getLastName();
        this.customerEmail = contract.getCustomer().getUser().getEmail();

        this.options = contract.getOptions()
                .stream()
                .map(OptionView::new)
                .sorted(Comparator.comparing(OptionView::getName))
                .collect(Collectors.toList());

        this.monthlyPrice = contract.getPlan().getConnectionPrice() + options
                .stream().mapToInt(OptionView::getPrice).sum();

        this.isActive = contract.isActive();

    }

    public Long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<OptionView> getOptions() {
        return options;
    }

    public void setOptions(List<OptionView> options) {
        this.options = options;
    }

    public Integer getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractView that = (ContractView) o;

        if (isActive != that.isActive) return false;
        if (!contractNumber.equals(that.contractNumber)) return false;
        if (!planName.equals(that.planName)) return false;
        if (!customerFullName.equals(that.customerFullName)) return false;
        if (!customerEmail.equals(that.customerEmail)) return false;
        return monthlyPrice.equals(that.monthlyPrice);
    }

    @Override
    public int hashCode() {
        int result = contractNumber.hashCode();
        result = 31 * result + planName.hashCode();
        result = 31 * result + customerFullName.hashCode();
        result = 31 * result + customerEmail.hashCode();
        result = 31 * result + monthlyPrice.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return contractNumber.toString();
    }
}
