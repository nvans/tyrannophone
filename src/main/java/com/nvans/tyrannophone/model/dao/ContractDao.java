package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Contract;

import java.util.Set;

public interface ContractDao extends GenericDao<Contract> {

    Set<Contract> getAllByCustomerId(Long customerId);

    Contract getContractByNumber(Long contractNumber);

    Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId);

}
