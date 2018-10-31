package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.PlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"contract", "currentPlanOpts", "plans", "lastPage"})
public class ContractController {

    private static final Logger log = Logger.getLogger(ContractController.class);

    private static final String FIRST_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";

    @Autowired
    ContractService contractService;

    @Autowired
    PlanService planService;

    @Autowired
    OptionService optionService;

    @Autowired
    CustomerService customerService;

    @Autowired
    Cart cart;

    @GetMapping("/contracts")
    public String getContracts(@RequestParam(defaultValue = FIRST_PAGE) Integer page,
                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                               Model model)
    {

        int lastPage = contractService.getLastPageNumber(pageSize);

        page = (page > lastPage) ? lastPage : page;

        model.addAttribute("page", page);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("contracts", contractService.getContractsPage(page, pageSize));

        return "contracts";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    @GetMapping("/contracts/{contractNumber}")
    public String editContract(@PathVariable Long contractNumber, Model model) {

        Contract contract = contractService.getContractByNumber(contractNumber);

        if (contract == null) {
            return "redirect:/contracts";
        }

        model.addAttribute("contract", contract);
        model.addAttribute("currentPlanOpts", new TreeMap<>(planService.getPlan(contract.getPlan().getPlanName()).getAvailableOptions()));
        model.addAttribute("plans", planService.getAllPlans());

        return "contracts/edit";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    @PostMapping("/contracts/edit")
    public String editContract(@ModelAttribute Contract contract, Model model) {

        try {
             cart.setContract(contract);
             cart.setPlan(planService.getPlan(contract.getPlan().getId()));

             List<String> connectedNames =
                     contract.getOptions().stream().map(Option::getName).collect(Collectors.toList());

             cart.setOptions(
                     optionService.getAvailableOptionsForPlan(contract.getPlan().getPlanName())
                     .stream().filter(opt -> connectedNames.contains(opt.getName())).collect(Collectors.toList()));


//            contractService.updateContract(contract);
        }
        catch (TyrannophoneException ex) {
            model.addAttribute("error", ex.getMessage());
            return "contracts/edit";
        }

        return "redirect:/cart";
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/contracts/add/{customerId}")
    public String addContract(@PathVariable Long customerId, Model model) {

        Contract contract = new Contract();
        CustomerDto customer = customerService.getCustomerById(customerId);


        model.addAttribute("customer", customer);
        model.addAttribute("contract", contract);

        List<PlanDto> plans = planService.getAllPlans();
        model.addAttribute("plans", planService.getAllPlans());
        model.addAttribute("currentPlanOpts",
                new TreeMap<>(planService.getPlan(plans.get(0).getPlanName()).getAvailableOptions()));

        return "contracts/add";
    }

    @Secured("ROLE_EMPLOYEE")
    @PostMapping("/contracts/add/{customerId}")
    public String addContract(@PathVariable Long customerId, @Valid @ModelAttribute Contract contract, BindingResult result) {

        if (result.hasErrors()) {
            return "contracts/add";
        }

        contractService.addContract(contract, customerId);

        return "redirect:/customers/" + contract.getCustomer().getId();
    }


    @PostMapping("/contracts/block")
    public String blockContract(@RequestParam Long contractNumber, @RequestParam String reason) {

        contractService.blockContract(contractNumber, reason);

        return "redirect:/contracts/" + contractNumber;

    }

    @GetMapping("/contracts/unblock/{contractNumber}")
    public String unblockContract(@PathVariable Long contractNumber) {

        contractService.unblockContract(contractNumber);

        return "redirect:/contracts/" + contractNumber;
    }


//    // TODO : move to options controller
//    // TODO : change path
//    @GetMapping(value = "/contracts/super", produces = "application/json")
//    public @ResponseBody List<PlanOptionDto> getAvailableOptionsByPlan(@RequestParam String planName) {
//
//        return optionService.getAvailableOptionsForPlan(planName);
//    }

    // TODO : move to options controller
    // TODO : change path
    @GetMapping(value = "/contracts/super", produces = "application/json")
    public @ResponseBody PlanDto getAvailableOptionsByPlan(@RequestParam String planName) {

        return planService.getPlanDto(planName);
    }

}
