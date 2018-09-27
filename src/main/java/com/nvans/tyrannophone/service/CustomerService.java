package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    /**
     * The method retrieves details of the authenticated customer.
     *
     * @return authenticated customer details, null - if current user hasn't "ROLE_CUSTOMER" returns
     */
    Customer getCustomerDetails();


    Customer getCustomerById(Long id);

    /**
     * The method retrieves list of all customers.
     *
     * @return customers list
     */
    List<Customer> getAllCustomers();

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
    Customer getCustomerByContractNumber(Long contractNumber);


    Customer getCustomerByEmail(String email);

    /**
     * The method changes the customer state to inactive
     *
     * @param customer - the customer object
     */
    void blockCustomer(Customer customer);


    void unblockCustomer(Customer customer);

    void updateCustomer(Customer customer);
}
