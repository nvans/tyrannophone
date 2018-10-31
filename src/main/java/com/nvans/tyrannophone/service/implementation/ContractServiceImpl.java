package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.IncompatibleOptionsException;
import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.dao.*;
import com.nvans.tyrannophone.model.dto.ContractView;
import com.nvans.tyrannophone.model.entity.*;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    CustomerDao customerDao;

    @Autowired
    OptionDao optionDao;

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

        if (currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY)) {

            return contractDao.getContractByNumber(contractNumber);
        }
        else if (currentUser.getAuthorities().contains(ApplicationAuthorities.CUSTOMER_AUTHORITY)) {

            return contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getAllContracts() {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY)) {

            return contractDao.findAll();
        }


        return contractDao.getAllByCustomerId(currentUser.getUserId());

    }

    @Override
    public List<Contract> getContractsPage(int pageNumber, int pageSize) {

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Calculate last page
        long contractTotal = contractDao.count();

        double lastPageDouble = ((double) contractTotal) / pageSize;
        int lastPage = lastPageDouble < 1 ? 1 : (int) Math.ceil(lastPageDouble);

        if (pageNumber > lastPage) {
            pageNumber = lastPage;
        }

        if (currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY)) {
            return contractDao.getContractPage(pageNumber, pageSize);
        }


        return contractDao.getAllByCustomerId(currentUser.getUserId());
    }

    @Override
    public int getLastPageNumber(int pageSize) {

        long contractTotal = contractDao.count();

        double lastPage = ((double) contractTotal) / pageSize;
        lastPage = lastPage < 1 ? 1 : lastPage;

        return (int) Math.ceil(lastPage);
    }

    @Override
    public void blockContract(Long contractNumber, String reason) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        boolean isEmployee = currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);

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
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        User blockInitiator = contract.getBlockDetails().getBlockedByUser();

        boolean isSelfUnblock = blockInitiator != null && blockInitiator.getId().equals(currentUser.getUserId());
        boolean isEmployee = currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);

        if (isEmployee || isSelfUnblock) {
            contract.setActive(true);
            contract.setBlockDetails(null);
        }
    }

    @Override
    public void addContract(Contract contract, Long customerId) {

        if (contract.getContractNumber() == null) {
            throw new TyrannophoneException("Contract number mustn't be empty");
        }

        if (contractDao.getContractByNumber(contract.getContractNumber()) != null) {
            throw new TyrannophoneException("Contract with number'" + contract.getContractNumber() + "' already exists");
        }

//        if(! contract.getPlan().getAvailableOptions().keySet().containsAll(contract.getOptions())){
//            throw new IncompatibleOptionsException("These options not available for this plan");
//        }

        Set<Option> optionsToConnect = new HashSet<>();

        for (Option opt : contract.getOptions()) {
            optionsToConnect.addAll(optionsGraph.getParents(opt));
        }

        if(! optionValidationService.isOptionsCompatible(optionsToConnect)) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        if (!contract.isActive() && contract.getBlockDetails() == null) {
            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setReason("Not activated yet.");
            contract.setBlockDetails(blockDetails);
        }

        Customer customer = customerDao.findById(customerId);

        if (customer == null) {
            throw new TyrannophoneException("Customer can't be null");
        }

        contract.setOptions(optionsToConnect);
        customer.setBalance(customer.getBalance() - contract.getMonthlyPrice());

        contract.setCustomer(customer);
        contractDao.update(contract);
    }

    @Override
    public void updateContract(Contract contract) {

        if (!(contract.isActive())) {
            throw new TyrannophoneException("The contract is blocked!");
        }

        Plan plan = planDao.findByName(contract.getPlan().getPlanName());

        Set<Option> availableOptions = plan.getAvailableOptions().keySet();
        Set<Option> mandatoryOptions = plan.getAvailableOptions().entrySet()
                .stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<Option> connectedOptions = contract.getOptions();

        if(!(availableOptions.containsAll(connectedOptions) && connectedOptions.containsAll(mandatoryOptions))){
            throw new IncompatibleOptionsException("These options are not available for this plan");
        }

        Set<Option> optionsToConnect = new HashSet<>();

        for (Option opt : contract.getOptions()) {
            optionsToConnect.addAll(optionsGraph.getParents(opt));
        }

        if(!(optionValidationService.isOptionsCompatible(optionsToConnect))) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        Contract contractPO = contractDao.getContractByNumber(contract.getContractNumber());
        contractPO.setPlan(contract.getPlan());
        contractPO.setOptions(optionsToConnect);
    }

    @Override
    public ContractView getContractView(Long contractNumber) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = null;

        if (currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY)) {
            contract = contractDao.getContractByNumber(contractNumber);
        }
        else if (currentUser.getAuthorities().contains(ApplicationAuthorities.CUSTOMER_AUTHORITY)) {
            contract = contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());
        }

        if (contract != null) {
            return new ContractView(contract);
        }

        return null;
    }
}
