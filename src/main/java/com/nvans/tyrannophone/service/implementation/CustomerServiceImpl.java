package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private GenericDao<Contract> contractDao;

    @Autowired
    private GenericDao<Customer> customerDao;


    @Override
    public Customer getCustomerDetails(Long customerId) {

        return customerDao.findById(customerId);
    }

    @Override
    public Set<Contract> getContracts(Long customerId) {

        List<Contract> contr = contractDao.findAllByParam("customer.id", customerId);

        Set<Contract> contracts = new HashSet<>(contr);

        return contracts;

    }
}
