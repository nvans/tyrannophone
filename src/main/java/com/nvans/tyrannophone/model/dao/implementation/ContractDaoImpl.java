package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.entity.Contract;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ContractDaoImpl
        extends AbstractGenericDao<Contract> implements ContractDao {

    private static final Logger logger = Logger.getLogger(ContractDaoImpl.class);

    public ContractDaoImpl() {
        super(Contract.class);
    }

    @Override
    public List<Contract> getAllByCustomerId(Long customerId) {

        logger.debug("Getting contracts by customer id = " + customerId);

        TypedQuery<Contract> query = entityManager.createQuery(
                "select c from Contract c " +
                "where c.customer.id = :customerId", Contract.class);
        query.setParameter("customerId", customerId);

        return query.getResultList();
    }

    @Override
    public Contract getContractByNumber(Long contractNumber) {

        logger.debug("Getting contract by number = " + contractNumber);

        TypedQuery<Contract> query = entityManager.createQuery(
                "select c from Contract c " +
                "LEFT JOIN FETCH c.options " +
                "WHERE c.contractNumber = :contractNumber", Contract.class);
        query.setParameter("contractNumber", contractNumber);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException ex) {

            logger.debug("Contract '" + contractNumber + "' not found");

            return null;
        }
    }

    @Override
    public Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId) {

        logger.debug("Getting contract by number '" + contractNumber + "' for customer with id = " + customerId);

        TypedQuery<Contract> query = entityManager.createQuery(
            "select c from Contract c " +
            "LEFT JOIN FETCH c.options " +
            "where c.contractNumber = :contractNumber " +
                    "and c.customer.id = :customerId", Contract.class);

        query.setParameter("contractNumber", contractNumber);
        query.setParameter("customerId", customerId);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            logger.debug("Contract'" + contractNumber + "' not found");

            return null;
        }
    }
}
