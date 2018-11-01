package com.nvans.tyrannophone.model.cart;

import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;

import java.io.Serializable;

/**
 * Represents cart's item
 *
 */
public class CartItem implements Serializable {

    private CustomerDto customer;

    private ContractDto contract;

    public CartItem() {

    }

    public CartItem(CustomerDto customer, ContractDto contract) {

        if (customer == null) throw new IllegalArgumentException("Customer can't be null");
        if (contract == null) throw new IllegalArgumentException("Contract can't be null");

        this.customer = customer;
        this.contract = contract;

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
}
