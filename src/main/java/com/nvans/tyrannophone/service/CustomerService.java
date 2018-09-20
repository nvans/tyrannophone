package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Contract;

import java.util.Set;

public interface CustomerService {

    CustomerDto getCustomerDetails();
    Set<Contract> getContracts();

}
