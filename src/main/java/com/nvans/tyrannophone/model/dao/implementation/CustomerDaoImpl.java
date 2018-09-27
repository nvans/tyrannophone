package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CustomerDaoImpl
        extends AbstractGenericDao<Customer> implements CustomerDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);

    public CustomerDaoImpl() {
        super(Customer.class);
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery(
            "SELECT c " +
            "FROM Customer c LEFT JOIN FETCH c.contracts " +
            "WHERE c.id = :id", Customer.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            LOGGER.debug("No customer with id " + id);

            return null;
        }
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

        try {
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            LOGGER.debug("Customer with contract '" + contractNumber + "' doesn't exist");

            return null;
        }
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
