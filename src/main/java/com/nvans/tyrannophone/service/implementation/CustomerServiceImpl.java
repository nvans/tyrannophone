package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.CustomerRegistrationDto;
import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.SessionService;
import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private GenericDao<User> userDao;

    @Autowired
    private GenericDao<Role> roleDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerCustomer(CustomerRegistrationDto registrationDto) {

        User user = new User();
        user.setUserName(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setActive(true);
        user.setRoles(Collections.singleton(roleDao.findByParam("role", "ROLE_CUSTOMER")));

        userDao.save(user);

        Customer customer = new Customer();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setPassport(registrationDto.getPassport());
        customer.setAddress(registrationDto.getAddress());
        customer.setBalance(0);
        customer.setUser(user);

        customerDao.save(customer);

    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerDetails() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customerDao.findByIdEager(currentUser.getUserId());
    }


    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {

        Customer customer = customerDao.findById(id);


        if (customer != null) {
            return new CustomerDto(customer);
        }

        return null;
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
    @Secured("ROLE_EMPLOYEE")
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
    public void blockCustomer(Long customerId, String reason) {

        log.info("Blocking customer with id{" + customerId + "}");

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BlockDetails blockDetails = new BlockDetails();
        blockDetails.setReason(reason == null ? "-" : reason);
        blockDetails.setBlockedByUser(userDao.findById(currentUser.getUserId()));

        User customerUser = userDao.findById(customerId);

        if (customerUser != null) {

            customerUser.setActive(false);
            customerUser.setBlockDetails(blockDetails);

            userDao.update(customerUser);

            log.info("Async method run");

            sessionService.invalidateSession(customerId);

            log.info("After async");
        }



    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void unblockCustomer(Long customerId) {

        log.info("Unblocking for user{" + customerId + "}");

        User customerUser = userDao.findById(customerId);

        if (customerUser != null) {
            customerUser.setActive(true);
            customerUser.setBlockDetails(null);
            userDao.update(customerUser);
        }

        log.info("Unblocking for user{" + customerId + "} completed");
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {

        Customer customer = customerDao.findById(customerDto.getCustomerId());

        customer.getUser().setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setPassport(customerDto.getPassport());

        customerDao.update(customer);
    }

    @Override
    public List<Customer> getCustomersPage(Integer pageNumber, Integer pageSize) {

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        // Calculate last page
        long customersTotal = customerDao.count();

        double lastPageDouble = ((double) customersTotal) / pageSize;
        int lastPage = lastPageDouble < 1 ? 1 : (int) Math.ceil(lastPageDouble);

        if (pageNumber > lastPage) {
            pageNumber = lastPage;
        }

        return customerDao.getCustomersPage(pageNumber, pageSize);
    }

    @Override
    public int getLastPageNumber(Integer pageSize) {

        long contractTotal = customerDao.count();

        double lastPage = ((double) contractTotal) / pageSize;
        lastPage = lastPage < 1 ? 1 : lastPage;

        return (int) Math.ceil(lastPage);
    }
}
