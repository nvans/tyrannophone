package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_EMPLOYEE")
@RequestMapping("/customers")
@SessionAttributes({"customer", "customers"})
public class CustomersController {

    @Autowired
    CustomerService customerService;

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
    public String editCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {

        if (result.hasErrors()) {
            // todo
            System.out.println();
        }

        customerService.updateCustomer(customer);

        return "customers/edit";
    }

    @PostMapping("/block")
    public String blockCustomer(@ModelAttribute Customer customer) {

        customerService.blockCustomer(customer);

        return "redirect:/customers/" + customer.getId();
    }

    @PostMapping("/unblock")
    public String unblockCustomer(@ModelAttribute Customer customer) {

        customerService.unblockCustomer(customer);

        return "redirect:/customers/" + customer.getId();
    }

}
