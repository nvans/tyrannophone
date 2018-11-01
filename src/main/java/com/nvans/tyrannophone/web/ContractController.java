package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.CustomerInfo;
import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.OrderStatus;
import com.nvans.tyrannophone.model.OrderType;
import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.cart.OrderCart;
import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.service.*;
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

@Controller
@SessionAttributes({"contract", "currentPlanOpts", "plans", "lastPage"})
public class ContractController {

    private static final Logger log = Logger.getLogger(ContractController.class);

    private static final String FIRST_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";



    @Autowired
    private OrderService orderService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private PlanService planService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Cart cart;

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

        ContractDto contract = contractService.getContractDtoByNumber(contractNumber);

        if (contract == null) {
            return "redirect:/contracts";
        }

        model.addAttribute("contract", contract);
        model.addAttribute("customer", customerService.getCustomerByContractNumber(contract.getContractNumber()));
        model.addAttribute("currentPlanOpts", planService.getPlanDto(contract.getPlan().getPlanName()).getAvailableOptions());
        model.addAttribute("plans", planService.getAllPlans());

        return "contracts/edit";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
    @PostMapping("/contracts/edit")
    public String editContract(@ModelAttribute("contract") ContractDto contractDto, BindingResult result) {

        if (result.hasErrors()) {
            return "contracts/edit";
        }

        CustomerDto customerDto = customerService.getCustomerDtoByContractNumber(contractDto.getContractNumber());
        ContractDto existedContractDto = contractService.getContractDtoByNumber(contractDto.getContractNumber());

        // Determination of order type
        if (!(existedContractDto.getPlan().getId().equals(contractDto.getPlan().getId()))) {
            orderService.createPlanOrder(customerDto, contractDto);
        }
        else {
            orderService.createOptionsOrder(customerDto, contractDto);
        }

        return "redirect:/contracts";
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/contracts/add/{customerId}")
    public String addContract(@PathVariable Long customerId, Model model) {

        ContractDto contract = new ContractDto();
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
    public String addContract(@PathVariable Long customerId,
                              @Valid @ModelAttribute ContractDto contract,
                              BindingResult result) {

        if (result.hasErrors()) {
            return "contracts/add";
        }

        CustomerDto customer = customerService.getCustomerById(customerId);

        orderService.createContractOrder(customer, contract);

        return "redirect:/customers/" + customerId;
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
