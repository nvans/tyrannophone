package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CustomerDaoImpl
        extends AbstractGenericDao<Customer> implements CustomerDao {

    private final static Logger log = LoggerFactory.getLogger(CustomerDaoImpl.class);

    public CustomerDaoImpl() {
        super(Customer.class);
    }

//    @Override
//    public Customer findById(Long id) {
//        TypedQuery<Customer> query = entityManager.createQuery(
//            "SELECT c " +
//            "FROM Customer c " +
//            "LEFT JOIN FETCH c.contracts " +
//            "LEFT JOIN FETCH c.user " +
//            "LEFT JOIN FETCH c.user.blockDetails " +
//            "WHERE c.id = :id", Customer.class);
//        query.setParameter("id", id);
//
//        try {
//            return query.getSingleResult();
//        }
//        catch (NoResultException ex) {
//            log.debug("No customer with id " + id);
//
//            return null;
//        }
//    }

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

        List<Customer> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public Customer getCustomerByEmail(String email) {

        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Customer c " +
                "WHERE c.user.email = :email", Customer.class);
        query.setParameter("email", email);

        List<Customer> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public Customer findByIdEager(Long userId) {

        TypedQuery<Customer> query = entityManager.createQuery(
          "SELECT c " +
          "FROM Customer c " +
          "LEFT JOIN FETCH c.contracts " +
          "WHERE c.id = :userId", Customer.class);
        query.setParameter("userId", userId);

        List<Customer> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public List<Customer> getCustomersPage(Integer pageNumber, Integer pageSize) {

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        int firstResult = (pageNumber - 1) * pageSize;

        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c", Customer.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }


}
