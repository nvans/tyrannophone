package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.CustomerRegistrationDto;
import com.nvans.tyrannophone.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    void registerCustomer(CustomerRegistrationDto registrationDto);

    /**
     * The method retrieves details of the authenticated customer.
     *
     * @return authenticated customer details, null - if current user hasn't "ROLE_CUSTOMER" returns
     */
    Customer getCustomerDetails();


    CustomerDto getCustomerById(Long id);

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
     * @param customerId - the customer's id
     */
    void blockCustomer(Long customerId, String reason);

    void unblockCustomer(Long customerId);

    void updateCustomer(CustomerDto customerDto);

    List<Customer> getCustomersPage(Integer page, Integer pageSize);

    int getCustomersLastPageNumber(Integer pageSize);

    CustomerDto getCustomerDtoByContractNumber(Long contractNumber);
}
