package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/test")
    @Secured("ROLE_EMPLOYEE")
    public String testForm() {

//        customerService.blockCustomer("cust1@tmail.tm");
//        customerService.unblockCustomer("cust1@tmail.tm");
        CustomerDto customer = customerService.getCustomerByContractNumber(70002223344L);

        return "test";
    }
}
