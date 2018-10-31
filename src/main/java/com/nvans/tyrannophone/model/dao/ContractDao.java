package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Contract;

import java.util.List;

public interface ContractDao extends GenericDao<Contract> {

    List<Contract> getAllByCustomerId(Long customerId);

    Contract getContractByNumber(Long contractNumber);

    Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId);

    List<Contract> getContractPage(int pageNumber, int pageSize);

}
