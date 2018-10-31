package com.nvans.tyrannophone.model;

import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long contract;

    private PlanDto plan;

    private List<PlanOptionDto> options;

    private Integer price;

    private LocalDateTime orderDate;



    public Long getContract() {
        return contract;
    }

    public void setContract(Long contract) {
        this.contract = contract;
    }

    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

    public List<PlanOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<PlanOptionDto> options) {
        this.options = options;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
