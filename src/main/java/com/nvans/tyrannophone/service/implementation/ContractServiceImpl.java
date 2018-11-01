package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.IncompatibleOptionsException;
import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.CustomerInfo;
import com.nvans.tyrannophone.model.dao.*;
import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.ContractView;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.*;
import com.nvans.tyrannophone.model.security.TyrannophoneUser;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.UserPrincipalFacade;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.PlanService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    private static final Logger log = Logger.getLogger(ContractServiceImpl.class);

    @Autowired
    private UserPrincipalFacade userPrincipalFacade;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private GenericDao<User> userDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private PlanService planService;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private OptionsGraph optionsGraph;

    @Autowired
    private OptionValidationService optionValidationService;

    @Override
    @Transactional(readOnly = true)
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public Contract getContractByNumberAndCustomerId(Long contractNumber, Long customerId) {
        log.info("GetContractByNumberAndCustomerId");

        return contractDao.getContractByNumberAndCustomerId(contractNumber, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public Contract getContractByNumber(Long contractNumber) {

        log.info("GetContractByNumber");

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        if (currentUser.isEmployee()) {

            return contractDao.getContractByNumber(contractNumber);
        }
       
        return contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public ContractDto getContractDtoByNumber(Long contractNumber) {

        log.info("get contract dto by number");

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        if (currentUser.isEmployee()) {

            return new ContractDto(contractDao.getContractByNumber(contractNumber));
        }

        return new ContractDto(contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId()));
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    @Transactional(readOnly = true)
    public List<Contract> getAllContracts() {

        log.info("get all contracts");

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        if (currentUser.isEmployee()) {

            return contractDao.findAll();
        }

        return contractDao.getAllByCustomerId(currentUser.getUserId());

    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public List<Contract> getContractsPage(int pageNumber, int pageSize) {

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        // Calculate last page
        long contractTotal = contractDao.count();

        double lastPageDouble = ((double) contractTotal) / pageSize;
        int lastPage = lastPageDouble < 1 ? 1 : (int) Math.ceil(lastPageDouble);

        if (pageNumber > lastPage) {
            pageNumber = lastPage;
        }

        if (currentUser.isEmployee()) {
            return contractDao.getContractPage(pageNumber, pageSize);
        }

        return contractDao.getAllByCustomerId(currentUser.getUserId());
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public int getLastPageNumber(int pageSize) {

        long contractTotal = contractDao.count();

        double lastPage = ((double) contractTotal) / pageSize;
        lastPage = lastPage < 1 ? 1 : lastPage;

        return (int) Math.ceil(lastPage);
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public void blockContract(Long contractNumber, String reason) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        boolean isSelfBlock = currentUser.getUserId().equals(contract.getCustomer().getId());

        if (currentUser.isEmployee() || isSelfBlock) {
            contract.setActive(false);

            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setBlockedByUser(userDao.findById(currentUser.getUserId()));
            blockDetails.setReason(reason);

            contract.setBlockDetails(blockDetails);
        }
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public void unblockContract(Long contractNumber) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        User blockInitiator = contract.getBlockDetails().getBlockedByUser();

        boolean isSelfUnblock = (blockInitiator != null) && (blockInitiator.getId().equals(currentUser.getUserId()));

        if (currentUser.isEmployee() || isSelfUnblock) {
            contract.setActive(true);
            contract.setBlockDetails(null);
        }
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void addContract(ContractDto contract, Long customerId) {

        log.info("add contract");

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
        optionsToConnect.addAll(contract.getOptions().stream()
                .map(opt -> optionDao.findById(opt.getId())) // retrieve options
                .collect(Collectors.toList()));

        // Add parents
        optionsToConnect.forEach(option -> optionsToConnect.addAll(optionsGraph.getParents(option)));

        if(!(optionValidationService.areOptionsCompatible(optionsToConnect))) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        Contract contractToSave = new Contract();
        contractToSave.setContractNumber(contract.getContractNumber());
        contractToSave.setPlan(planDao.findById(contract.getPlan().getId()));
        contractToSave.setOptions(optionsToConnect);
        contractToSave.setActive(contract.isActive());

        if (!contractToSave.isActive()) {
            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setReason("Not activated yet.");
            contractToSave.setBlockDetails(blockDetails);
        }

        Customer customer = customerDao.findById(customerId);
        customer.setBalance(customer.getBalance() - contract.getPrice());

        contractToSave.setCustomer(customer);
        contractDao.save(contractToSave);

        customerInfo.update();
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public void updateContractFull(ContractDto contract) {

        log.info("Update contract full");

        if (!(contract.isActive())) {
            throw new TyrannophoneException("The contract is blocked!");
        }

        PlanDto plan = planService.getPlan(contract.getPlan().getId());

        List<PlanOptionDto> availableOptions = plan.getAvailableOptions();
        List<PlanOptionDto> mandatoryOptions = plan.getConnectedOptions();

        List<PlanOptionDto> connectedOptions = contract.getOptions();

        if(!(availableOptions.containsAll(connectedOptions) && connectedOptions.containsAll(mandatoryOptions))){
            throw new IncompatibleOptionsException("These options are not available for this plan");
        }

        Set<Option> optionsToConnect = new HashSet<>();
        contract.getOptions().forEach(option -> {
            Option optionPO = optionDao.findById(option.getId());

            optionsToConnect.add(optionPO);
            optionsToConnect.addAll(optionsGraph.getParents(optionPO));
        });


        if(!(optionValidationService.areOptionsCompatible(optionsToConnect))) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        Contract contractPO = contractDao.getContractByNumber(contract.getContractNumber());
        contractPO.setPlan(planDao.findById(plan.getId()));
        contractPO.setOptions(optionsToConnect);

        Customer customer = customerDao.getByContractNumber(contract.getContractNumber());

        Integer newBalance = customer.getBalance() - contract.getPrice();

        if (newBalance < 0) {
            throw new TyrannophoneException("Not enough balance");
        }

        customer.setBalance(newBalance);

        contractDao.save(contractPO);

        customerInfo.update();
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public void updateContractOptions(ContractDto contract) {

        log.info("Update contract options");

        if (!(contract.isActive())) {
            throw new TyrannophoneException("The contract is blocked!");
        }

        PlanDto plan = planService.getPlan(contract.getPlan().getId());

        List<PlanOptionDto> availableOptions = plan.getAvailableOptions();
        List<PlanOptionDto> mandatoryOptions = plan.getConnectedOptions();

        List<PlanOptionDto> connectedOptions = contract.getOptions();


        if(!(availableOptions.containsAll(connectedOptions) && connectedOptions.containsAll(mandatoryOptions))){
            throw new IncompatibleOptionsException("These options are not available for this plan");
        }

        Set<Option> optionsToConnect = new HashSet<>();
        optionsToConnect.addAll(connectedOptions.stream()
                .map(opt -> optionDao.findById(opt.getId()))
                .collect(Collectors.toSet()));

        if(!(optionValidationService.areOptionsCompatible(optionsToConnect))) {
            throw new IncompatibleOptionsException("Selected options are incompatible");
        }

        Contract contractPO = contractDao.getContractByNumber(contract.getContractNumber());
        contractPO.setPlan(planDao.findById(plan.getId()));


        Set<Option> newOptions = new HashSet<>(optionsToConnect);
        newOptions.removeAll(contractPO.getOptions());


        // Cost =  ConnectedOptions - toConnect
        int totalCost = newOptions.stream().mapToInt(Option::getPrice).sum();


        totalCost = totalCost < 0 ? 0 : totalCost;

        contractPO.setOptions(optionsToConnect);

        Customer customer = customerDao.getByContractNumber(contract.getContractNumber());

        Integer newBalance = customer.getBalance() - totalCost;

        if (newBalance < 0) {
            throw new TyrannophoneException("Not enough balance");
        }

        customer.setBalance(newBalance);

        contractDao.save(contractPO);

        customerInfo.update();
    }

    @Override
    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    public ContractView getContractView(Long contractNumber) {

        TyrannophoneUser currentUser = userPrincipalFacade.currentUser();

        Contract contract = currentUser.isEmployee() ?
                contractDao.getContractByNumber(contractNumber) :
                contractDao.getContractByNumberAndCustomerId(contractNumber, currentUser.getUserId());

        return (contract != null) ? new ContractView(contract) : null;
    }
}
