package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.service.BlockService;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlockService blockService;

    @GetMapping("/profile")
    public ModelAndView getProfile() {

        CustomerDto customer = customerService.getCustomerDetails();

        return new ModelAndView("customer/profile", "customer", customer);
    }



    @GetMapping("/contracts")
    public ModelAndView getContracts() {

        Set<Contract> contracts = customerService.getContracts();

        ModelAndView modelView = new ModelAndView("customer/contracts");
        modelView.addObject("contracts", contracts);

        return modelView;

    }

    @GetMapping("/contracts/{contractNumber}")
    public RedirectView editContract(@PathVariable Long contractNumber, RedirectAttributes attributes) {

        ContractDto contractDto = contractService.getContractByNumber(contractNumber);

        attributes.addFlashAttribute("contract", contractDto);

        return new RedirectView("edit");

    }

    @GetMapping("/contracts/edit")
    public ModelAndView editContract(@ModelAttribute("contract") ContractDto contractDto) {

        return new ModelAndView("/customer/contracts/edit", "contract", contractDto);
    }

    @PostMapping("/contracts/block")
    public RedirectView blockContract(@RequestParam Long contractNumber, @RequestParam String reason) {

        blockService.blockContract(contractNumber, reason);

        return new RedirectView(contractNumber.toString());

    }

    @GetMapping("/contracts/unblock/{contractNumber}")
    public RedirectView unblockContract(@PathVariable Long contractNumber) {

        blockService.unblockContract(contractNumber);

        return new RedirectView("../" + contractNumber);
    }
}
