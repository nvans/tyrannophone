package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.CustomerRegistrationDto;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@SessionAttributes({"plans", "customer", "registrationDto"})
public class MainController {

    @Autowired
    PlanService planService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ContractService contractService;

    @RequestMapping("/home")
    public String defaultPage(Model model) {

        model.addAttribute("plans", planService.getAllPlans());

        return "home";

    }

    @GetMapping("/profile")
    public String getProfile(Model model) {

        Customer customer = customerService.getCustomerDetails();

        model.addAttribute("customer", customer);
        model.addAttribute("contracts", contractService.getAllContracts());

        return "customer/profile";
    }

    @PostMapping("/profile")
    public String editCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {

        if (result.hasErrors()) {
            // todo
            System.out.println();
        }

        // TODO
//        customerService.updateCustomer(customer);

        return "customer/profile";
    }

    @GetMapping("/registration")
    public String registerCustomer(Model model) {

        model.addAttribute("registration", new CustomerRegistrationDto());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerCustomer(@Valid @ModelAttribute("registration") CustomerRegistrationDto registration,
                                   BindingResult result, Model model) {

        if (!(registration.getEmail().equals(registration.getConfirmEmail()))) {
            result.rejectValue("confirmEmail", null, "The email fields must match");
        }

        if (!(registration.getPassword().equals(registration.getConfirmPassword()))) {
            result.rejectValue("confirmPassword", null, "The password fields must match");
        }

        if (result.hasErrors()) {

            return "registration";
        }

        customerService.registerCustomer(registration);

        return "redirect: /home";
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView model = new ModelAndView();

        // check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);

            model.addObject("username", userDetail.getUsername());

        }

        model.setViewName("403");
        return model;

    }

    @GetMapping(value = "/404")
    public String notFound() {

        return "404";
    }
}
