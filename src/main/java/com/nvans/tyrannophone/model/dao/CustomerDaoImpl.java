package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.security.auth.login.Configuration;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CustomerDaoImpl
        extends AbstractGenericDao<Customer> implements CustomerDao{

    public CustomerDaoImpl() {
        super(Customer.class);
    }

    @Override
    public Set<Contract> getContracts(Long customerId) {

        TypedQuery<Contract> query = entityManager
                .createQuery("SELECT c FROM Contract c WHERE c.customer.id=:customerId", Contract.class)
                .setParameter("customerId", customerId);


        return new HashSet<>(query.getResultList());
    }
}
