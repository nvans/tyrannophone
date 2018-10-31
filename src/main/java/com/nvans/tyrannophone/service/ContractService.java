package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.ContractView;
import com.nvans.tyrannophone.model.entity.Contract;

import java.util.List;

public interface ContractService {

    Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId);

    /**
     * The method retrieves the contract by the number.
     *
     *
     * @param contractNumber - number of the contract.
     * @return single contract representation.
     */
    Contract getContractByNumber(Long contractNumber);


    /**
     * The method retrieves list of all contracts.
     *
     * @return list of contracts.
     */
    List<Contract> getAllContracts();

    List<Contract> getContractsPage(int pageNumber, int pageSize);

    int getLastPageNumber(int pageSize);

    /**
     * The method sets the state of the contract to inactive.
     *
     * @param contractNumber - number of the contract.
     */
    void blockContract(Long contractNumber, String reason);

    /**
     * The method sets the state of the contract to active.
     *
     *
     * @param contractNumber - number of the contract.
     */
    void unblockContract(Long contractNumber);


    void addContract(Contract contract, Long customerId);

    void updateContract(Contract contract);

    ContractView getContractView(Long contractNumber);
}
