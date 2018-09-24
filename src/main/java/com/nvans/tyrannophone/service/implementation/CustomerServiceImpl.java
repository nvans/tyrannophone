package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.CustomerFullDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private GenericDao<User> userDao;

    @Autowired
    private GenericDao<Role> roleDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerDetails() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.findById(currentUser.getUserId());

        if (user != null && user.getDetails() != null && user.getDetails() instanceof Customer) {
            return new CustomerDto(user);
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Contract> getContracts() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return contractDao.getAllByCustomerId(currentUser.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerFullDto getCustomerById(Long id) {

        Customer customer = customerDao.findById(id);

        return new CustomerFullDto(customer);

    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public List<CustomerFullDto> getAllCustomers() {

        List<Customer> customers = customerDao.findAll();

        return customers.stream()
                .map(CustomerFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void addNewCustomer(CustomerDto customerDto) {

        // TODO: Customer registration DTO
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public CustomerDto getCustomerByContractNumber(Long contractNumber) {

        return new CustomerDto(customerDao.getByContractNumber(contractNumber));
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void blockCustomer(String email) {

        Customer customer = customerDao.getCustomerByEmail(email);

        if (customer != null) {
            customer.getUser().setActive(false);
        }

    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void unblockCustomer(String email) {
        Customer customer = customerDao.getCustomerByEmail(email);

        if (customer != null) {
            customer.getUser().setActive(true);
        }
    }
}
