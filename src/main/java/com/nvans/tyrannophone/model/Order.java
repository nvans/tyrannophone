package com.nvans.tyrannophone.model;

import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import org.jgroups.util.Triple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private Long id;

    private CustomerDto customer;

    private ContractDto contract;

    private PlanDto plan;

    private List<PlanOptionDto> options;

    private List<PlanOptionDto> newOptions;

    private List<PlanOptionDto> removedOptions;

    private List<PlanOptionDto> existedOptions;

    private LocalDateTime orderDate;

    private OrderType orderType;

    private OrderStatus orderStatus;

    public Order() {

    }

    public Order(CustomerDto customer, ContractDto contract) {

        this.customer = customer;
        this.contract = contract;
        this.plan = contract.getPlan();
        this.options = contract.getOptions();
        this.orderStatus = OrderStatus.CREATED;
    }

    public Order(CustomerDto customer, ContractDto contract, List<PlanOptionDto> existedOptions) {
        this(customer, contract);

        this.existedOptions = existedOptions;

        this.newOptions = new ArrayList<>(options);
        newOptions.removeAll(existedOptions);

        this.removedOptions = new ArrayList<>(existedOptions);
        removedOptions.removeAll(options);

    }

    public Integer getPrice() {

        int totalPrice = 0;

        switch (orderType) {
            case CONTRACT:
            case PLAN:
                totalPrice = plan.getConnectionPrice() +
                        options.stream().mapToInt(PlanOptionDto::getPrice).sum();
                break;
            case OPTIONS:
                totalPrice = newOptions.stream().mapToInt(PlanOptionDto::getPrice).sum();
                break;
        }

        return totalPrice;
    }


    public List<Triple<String, String, Integer>> getInvoiceTriples() {

        List<Triple<String, String, Integer>> result = new ArrayList<>();

        Triple<String, String, Integer> contractTriple = new Triple<>("Contract", this.contract.toString(), 0);

        Triple<String, String, Integer> planTriple = null;
        List<Triple<String, String, Integer>> optionsTriples = new ArrayList<>();

        switch (orderType) {
            case CONTRACT:
                contractTriple.setVal2("Add " + this.contract.toString());
                result.add(contractTriple);

            case PLAN:
                planTriple = new Triple<>("Plan", "Add " + this.plan.getPlanName(), this.plan.getConnectionPrice());
                optionsTriples.addAll(options.stream().map(opt -> new Triple<>("Option", "Add " + opt.getName(), opt.getPrice())).collect(Collectors.toList()));
                break;

            case OPTIONS:
                planTriple = new Triple<>("Plan", this.plan.getPlanName(), 0);

                optionsTriples.addAll(newOptions.stream().map(opt -> new Triple<>("Option", "Add " + opt.getName(), opt.getPrice())).collect(Collectors.toList()));
                optionsTriples.addAll(removedOptions.stream().map(opt -> new Triple<>("Option", "Remove " + opt.getName(), 0)).collect(Collectors.toList()));
                break;
        }

        result.add(contractTriple);
        result.add(planTriple);
        result.addAll(optionsTriples);


        return result;
    }

    // Getters and setters ->
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(ContractDto contract) {
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
