package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.CustomerFullDto;
import com.nvans.tyrannophone.model.entity.Contract;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    /**
     * The method retrieves details of the authenticated customer.
     *
     * @return authenticated customer details, null - if current user hasn't "ROLE_CUSTOMER" returns
     */
    CustomerDto getCustomerDetails();

    // todo: move to contract service
    Set<Contract> getContracts();


    CustomerFullDto getCustomerById(Long id);
    /**
     * The method retrieves list of all customers.
     *
     * @return customers list
     */
    List<CustomerFullDto> getAllCustomers();

    /**
     * The method adds new customer to the system.
     *
     * @param customerDto - customer representation.
     */
    void addNewCustomer(CustomerDto customerDto);

    /**
     * The method retrieves a single customer by the contract number
     *
     * @param contractNumber - contract number
     *
     * @return Customer representation
     */
    CustomerDto getCustomerByContractNumber(Long contractNumber);

    /**
     * The method changes the customer state to inactive
     *
     * @param email - the customer's email
     */
    void blockCustomer(String email);

    /**
     * The method changes the customer state to active
     *
     * @param email - the customer's email
     */
    void unblockCustomer(String email);

}
