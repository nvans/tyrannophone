package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private GenericDao<User> userDao;

    @Autowired
    private GenericDao<Customer> customerDao;

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerDetails() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.findById(currentUser.getUserId());

        return new CustomerDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Contract> getContracts() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return contractDao.getAllByCustomerId(currentUser.getUserId());
    }
}
