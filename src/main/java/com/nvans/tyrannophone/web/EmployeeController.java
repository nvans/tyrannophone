package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.CustomerFullDto;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ModelAndView showCustomers() {

        List<CustomerFullDto> customers = customerService.getAllCustomers();

        return new ModelAndView("employee/customers", "customers", customers);
    }

    @GetMapping("/customers/edit/{customerId}")
    public ModelAndView editCustomer(@PathVariable("customerId") Long customerId) {

        ModelAndView modelAndView = new ModelAndView("employee/customers/edit");
        modelAndView.addObject("customer", customerService.getCustomerById(customerId));

        return modelAndView;
    }

    @PostMapping("/customers/block")
    public String blockCustomer(@RequestParam("email") String email) {

        customerService.blockCustomer(email);

        return "employee/customers";
    }


}
