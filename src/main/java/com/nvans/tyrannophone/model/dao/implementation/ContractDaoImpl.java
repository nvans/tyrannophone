package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ContractDaoImpl
        extends AbstractGenericDao<Contract> implements ContractDao {

    public ContractDaoImpl() {
        super(Contract.class);
    }

    @Override
    public Set<Contract> getAllByCustomerId(Long customerId) {

        TypedQuery<Contract> query = entityManager.createQuery(
                "select c from Contract c " +
                "where c.customer.id = :customerId", Contract.class);
        query.setParameter("customerId", customerId);

        return new HashSet<>(query.getResultList());
    }

    @Override
    public Contract getContractByNumber(Long contractNumber) {

        TypedQuery<Contract> query = entityManager.createQuery(
                "select c from Contract c " +
                "where c.contractNumber = :contractNumber", Contract.class);
        query.setParameter("contractNumber", contractNumber);

        return query.getSingleResult();
    }

    @Override
    public Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId) {

        TypedQuery<Contract> query = entityManager.createQuery(
            "select c from Contract c " +
            "where c.contractNumber = :contractNumber " +
                    "and c.customer.id = :customerId", Contract.class);

        query.setParameter("contractNumber", contractNumber);
        query.setParameter("customerId", customerId);

        return query.getSingleResult();
    }
}
