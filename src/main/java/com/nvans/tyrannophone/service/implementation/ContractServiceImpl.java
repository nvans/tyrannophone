package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDao contractDao;

    @Override
    @Transactional(readOnly = true)
    public Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId) {

        return contractDao.getContractByNumberAndCustomerId(contractNumber, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public ContractDto getContractByNumber(Long contractNumber) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract =
                contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());

        BlockDetails blockDetails = contract.getBlockDetails();

        boolean readOnly = blockDetails != null
                && !(blockDetails.getBlockedByUser().getId().equals(currentUser.getUserId()));


        return new ContractDto(contract, readOnly);
    }
}
