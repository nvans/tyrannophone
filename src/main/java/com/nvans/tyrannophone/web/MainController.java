package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.entity.Customer;
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
@SessionAttributes("plans")
public class MainController {

    @Autowired
    PlanService planService;

    @Autowired
    CustomerService customerService;

    @RequestMapping("/home")
    public String defaultPage(Model model) {

        if (!model.containsAttribute("plans")) {
            model.addAttribute("plans", planService.getAllPlans());
        }

        return "home";

    }

    @GetMapping("/profile")
    public ModelAndView getProfile() {

        Customer customer = customerService.getCustomerDetails();

        return new ModelAndView("customer/profile", "customer", customer);
    }

    @PostMapping("/profile")
    public String editCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {

        if (result.hasErrors()) {
            // todo
            System.out.println();
        }

        customerService.updateCustomer(customer);

        return "customers/edit";
    }


    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

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
}
