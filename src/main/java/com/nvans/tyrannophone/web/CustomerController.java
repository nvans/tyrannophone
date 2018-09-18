package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.CustomUserPrinciple;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    // TODO: Make Dto great again!
    @GetMapping("/profile")
    public ModelAndView getProfile(Authentication auth) {

        CustomUserPrinciple currentUser = (CustomUserPrinciple) auth.getPrincipal();
        Long id = currentUser.getUserId();

        User user = userService.getUser(id);
        Customer customer = customerService.getCustomerDetails(id);


        ModelAndView modelView = new ModelAndView("profile");

        Map<String, String> profileDetails = new HashMap<>();
        profileDetails.put("Email", user.getEmail());
        profileDetails.put("First name", customer.getFirstName());
        profileDetails.put("Last name", customer.getLastName());
        profileDetails.put("Address", customer.getAddress());
        profileDetails.put("Passport", customer.getPassport());

        modelView.addObject("details", profileDetails);

        return modelView;
    }



    @GetMapping("/contracts")
    public ModelAndView getContracts(Authentication auth) {

        CustomUserPrinciple currentUser = (CustomUserPrinciple) auth.getPrincipal();
        Long id = currentUser.getUserId();

        Set<Contract> contracts = customerService.getContracts(id);


        ModelAndView modelView = new ModelAndView("contracts");
        modelView.addObject("contracts", contracts);

        return modelView;

    }
}
