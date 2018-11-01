package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.CustomerRegistrationDto;
import com.nvans.tyrannophone.model.entity.*;
import com.nvans.tyrannophone.model.security.TyrannophoneUser;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.SessionService;
import com.nvans.tyrannophone.service.UserPrincipalFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private UserPrincipalFacade userPrincipalFacade;
    
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
        customer.setBalance(1000);
        customer.setUser(user);

        customerDao.save(customer);

    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerDetails() {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        return customerDao.findByIdEager(currentUser.getUserId());
    }


    @Override
    @Transactional(readOnly = true)
    @Secured({"ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    public CustomerDto getCustomerById(Long id) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Customer customer = null;

        if (currentUser.isEmployee() || currentUser.getUserId().equals(id)) {
            customer = customerDao.findById(id);
        }

        return (customer != null) ? new CustomerDto(customer) : null;
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
    @Transactional(readOnly = true)
    @Secured({"ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    public Customer getCustomerByContractNumber(Long contractNumber) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Customer customer = customerDao.getByContractNumber(contractNumber);

        if (currentUser.isEmployee()) {
            return customer;
        }
        else {

            Set<Long> customerContractsNumbers = customer.getContracts()
                    .stream().map(Contract::getContractNumber).collect(Collectors.toSet());

            return customerContractsNumbers.contains(contractNumber) ? customer : null;
        }
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

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        BlockDetails blockDetails = new BlockDetails();
        blockDetails.setReason(reason == null ? "-" : reason);
        blockDetails.setBlockedByUser(userDao.findById(currentUser.getUserId()));

        User customerUser = userDao.findById(customerId);

        if (customerUser != null) {

            customerUser.setActive(false);
            customerUser.setBlockDetails(blockDetails);

            userDao.update(customerUser);

            sessionService.invalidateSession(customerId);
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
    @Secured({"ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
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
    @Secured({"ROLE_EMPLOYEE"})
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
    @Secured({"ROLE_EMPLOYEE"})
    public int getCustomersLastPageNumber(Integer pageSize) {

        long contractTotal = customerDao.count();

        double lastPage = ((double) contractTotal) / pageSize;
        lastPage = lastPage < 1 ? 1 : lastPage;

        return (int) Math.ceil(lastPage);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    public CustomerDto getCustomerDtoByContractNumber(Long contractNumber) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Customer customer = customerDao.getByContractNumber(contractNumber);

        if (customer == null) return null;

        if (currentUser.isEmployee()) {
            return new CustomerDto(customer);
        }
        else {
            Set<Long> contractsNumbers = customer.getContracts()
                    .stream().map(Contract::getContractNumber).collect(Collectors.toSet());

            return (contractsNumbers.contains(contractNumber)) ? new CustomerDto(customer) : null;

        }
    }
}
