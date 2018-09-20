package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.entity.Contract;

public interface ContractService {

    Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId);
    ContractDto getContractByNumber(Long contractNumber);
}
