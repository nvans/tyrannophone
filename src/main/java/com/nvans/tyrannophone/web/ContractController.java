package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@SessionAttributes({"contract", "currentPlanOpts", "plans"})
public class ContractController {

    @Autowired
    ContractService contractService;

    @Autowired
    PlanService planService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/contracts")
    public String getContracts(Model model) {

        model.addAttribute("contracts", contractService.getAllContracts());

        return "contracts";
    }

    @GetMapping("/contracts/{contractNumber}")
    public String editContract(@PathVariable Long contractNumber, Model model) {

        Contract contract = contractService.getContractByNumber(contractNumber);

        if (contract == null) {
            return "redirect:/contracts";
        }

        model.addAttribute("contract", contract);
        model.addAttribute("currentPlanOpts", planService.getPlan(contract.getPlan().getPlanName()).getAvailableOptions());
        model.addAttribute("plans", planService.getAllPlans());

        return "contracts/edit";
    }

    @PostMapping("/contracts/edit")
    public String editContract(@ModelAttribute Contract contract, Model model) {

        try {
            contractService.updateContract(contract);
        }
        catch (TyrannophoneException ex) {
            model.addAttribute("error", ex.getMessage());
            return "contracts/edit";
        }

        return "redirect:/contracts/" + contract.getContractNumber();
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/contracts/add/{customerId}")
    public String addContract(@PathVariable Long customerId, Model model) {

        Contract contract = new Contract();
        Customer customer = customerService.getCustomerById(customerId);
        contract.setCustomer(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("contract", contract);
        model.addAttribute("plans", planService.getAllPlans());

        return "contracts/add";
    }

    @Secured("ROLE_EMPLOYEE")
    @PostMapping("/contracts/add")
    public String addContract(@Valid @ModelAttribute Contract contract, BindingResult result) {

        if (result.hasErrors()) {
            return "contracts/add";
        }

        contractService.addContract(contract);

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


    @PostMapping("/contracts/super")
    public @ResponseBody String getPlanAvailableOptions(@RequestParam String planName) {

        Plan plan = planService.getPlan(planName);
        Set<Option> options = plan.getAvailableOptions();

        return options.toString();
    }

}
