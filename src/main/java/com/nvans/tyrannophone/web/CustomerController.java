package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_EMPLOYEE")
@RequestMapping("/customers")
@SessionAttributes({"customer", "customers"})
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping
    public String showCustomers(Model model) {

        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);

        return "customers";
    }

    @PostMapping
    public String findCustomerByNumber(@RequestParam Long contractNumber, Model model, SessionStatus sessionStatus) {

        if (contractNumber == null) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "customers";
        }

        Customer customer = customerService.getCustomerByContractNumber(contractNumber);

        if (customer != null) {
            List<Customer> customers = new ArrayList<>();
            customers.add(customer);
            model.addAttribute("customers", customers);
        }
        else {
            model.addAttribute("message", "Customer with number '" + contractNumber + "' can't be found.");
        }

        sessionStatus.setComplete();

        return "customers";
    }

    @GetMapping("/{customerId}")
    public String editCustomer(@PathVariable("customerId") Long customerId, Model model) {

        model.addAttribute("customer", customerService.getCustomerById(customerId));

        return "customers/edit";
    }

    @PostMapping("/edit")
    public String editCustomer(@Valid @ModelAttribute CustomerDto customer, BindingResult result) {

        if (result.hasErrors()) {
            // todo
            System.out.println();
        }

        customerService.updateCustomer(customer);

        return "redirect:/customers/" + customer.getCustomerId();
    }

    @PostMapping("/block/{customerId}")
    public String blockCustomer(@PathVariable Long customerId, @RequestParam String reason) {

        customerService.blockCustomer(customerId, reason);

        return "redirect:/customers/" + customerId;
    }

    @PostMapping("/unblock/{customerId}")
    public String unblockCustomer(@PathVariable Long customerId) {

        customerService.unblockCustomer(customerId);

        return "redirect:/customers/" + customerId;
    }

}
