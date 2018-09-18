package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;

import java.util.Set;

public interface CustomerService {

    Customer getCustomerDetails(Long customerId);
    Set<Contract> getContracts(Long customerId);

}
