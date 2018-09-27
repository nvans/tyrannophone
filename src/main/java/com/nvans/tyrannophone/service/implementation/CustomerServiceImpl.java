package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private GenericDao<User> userDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerDetails() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customerDao.findById(currentUser.getUserId());
    }


    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {

        return customerDao.findById(id);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {

        return customerDao.findAll();
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void addNewCustomer(CustomerDto customerDto) {

        // TODO: Customer registration DTO
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public Customer getCustomerByContractNumber(Long contractNumber) {

        return customerDao.getByContractNumber(contractNumber);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {

        return customerDao.getCustomerByEmail(email);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void blockCustomer(Customer customer) {

        if (customer != null) {
            customer.getUser().setActive(false);
        }

        customerDao.update(customer);

    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void unblockCustomer(Customer customer) {

        if (customer != null) {
            customer.getUser().setActive(true);
        }

        customerDao.update(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {

        if (customer != null) {
            customerDao.update(customer);
        }
    }
}
