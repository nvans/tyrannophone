package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;

import java.util.Set;

public interface CustomerDao extends GenericDao<Customer> {

    Set<Contract> getContracts(Long customerId);

    Customer getByContractNumber(Long contractNumber);

    Customer getCustomerByEmail(String email);
}
