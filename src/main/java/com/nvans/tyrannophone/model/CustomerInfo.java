package com.nvans.tyrannophone.model;

import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * This class represents customer at navbar.
 *
 */
@Component
@SessionScope
public class CustomerInfo implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Autowired
    CustomerService customerService;

    private String name;

    private String balance;


    @PostConstruct
    public void update() {

        Customer customer = customerService.getCustomerDetails();

        if (customer != null) {
            this.name = customer.getFirstName() + " " + customer.getLastName();
            this.balance = "$" + customer.getBalance();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
