package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CustomerDaoImpl
        extends AbstractGenericDao<Customer> implements CustomerDao {

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

    @Override
    public Customer getByContractNumber(Long contractNumber) {

        TypedQuery<Customer> query = entityManager.createQuery(
                        "SELECT c.customer " +
                        "FROM Contract c " +
                        "WHERE c.contractNumber = :contractNumber", Customer.class);
        query.setParameter("contractNumber", contractNumber);

        return query.getSingleResult();
    }

    @Override
    public Customer getCustomerByEmail(String email) {

        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Customer c " +
                "WHERE c.user.email = :email", Customer.class);
        query.setParameter("email", email);

        return query.getSingleResult();
    }
}
