package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.IncompatibleOptionsException;
import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    GenericDao<User> userDao;

    @Autowired
    PlanDao planDao;

    @Autowired
    ContractDao contractDao;

    @Autowired
    OptionsGraph optionsGraph;

    @Autowired
    OptionValidationService optionValidationService;

    @Override
    @Transactional(readOnly = true)
    public Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId) {

        return contractDao.getContractByNumberAndCustomerId(contractNumber, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Contract getContractByNumber(Long contractNumber) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {

            return contractDao.getContractByNumber(contractNumber);
        }

        return contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getAllContracts() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {

            return contractDao.findAll();
        }


        return contractDao.getAllByCustomerId(currentUser.getUserId());

    }

    @Override
    public void blockContract(Long contractNumber, String reason) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        boolean isEmployee = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        boolean isSelfBlock =
                currentUser.getUserId().equals(contract.getCustomer().getId());

        if (isEmployee || isSelfBlock) {
            contract.setActive(false);

            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setBlockedByUser(userDao.findById(currentUser.getUserId()));
            blockDetails.setReason(reason);

            contract.setBlockDetails(blockDetails);
        }
    }

    @Override
    public void unblockContract(Long contractNumber) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        User blockInitiator = contract.getBlockDetails().getBlockedByUser();

        boolean isSelfUnblock = blockInitiator != null && blockInitiator.getId().equals(currentUser.getUserId());
        boolean isEmployee = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        if (isEmployee || isSelfUnblock) {
            contract.setActive(true);
            contract.setBlockDetails(null);
        }
    }

    @Override
    public void addContract(Contract contract) {

        if (contract.getContractNumber() == null) {
            throw new TyrannophoneException("Contract number mustn't be empty");
        }

        if (contractDao.getContractByNumber(contract.getContractNumber()) != null) {
            throw new TyrannophoneException("Contract with number'" + contract.getContractNumber() + "' already exists");
        }

        if(! contract.getPlan().getAvailableOptions().containsAll(contract.getOptions())){
            throw new IncompatibleOptionsException("These options not available for this plan");
        }

        Set<Option> optionsToConnect = new HashSet<>();

        for (Option opt : contract.getOptions()) {
            optionsToConnect.addAll(optionsGraph.getAllParents(opt));
        }

        if(! optionValidationService.isOptionsCompatible(optionsToConnect)) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        if (!contract.isActive() && contract.getBlockDetails() == null) {
            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setReason("Not activated yet.");
            contract.setBlockDetails(blockDetails);
        }



        contractDao.update(contract);
    }

    @Override
    public void updateContract(Contract contract) {

        if (! contract.isActive()) {
            throw new TyrannophoneException("The contract is blocked!");
        }

        if(! contract.getPlan().getAvailableOptions().containsAll(contract.getOptions())){
            throw new IncompatibleOptionsException("These options not available for this plan");
        }

        Set<Option> optionsToConnect = new HashSet<>();

        for (Option opt : contract.getOptions()) {
            optionsToConnect.addAll(optionsGraph.getAllParents(opt));
        }

        if(! optionValidationService.isOptionsCompatible(optionsToConnect)) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        Contract contractPO = contractDao.getContractByNumber(contract.getContractNumber());
        contractPO.setPlan(contract.getPlan());
        contractPO.setOptions(optionsToConnect);
    }
}
