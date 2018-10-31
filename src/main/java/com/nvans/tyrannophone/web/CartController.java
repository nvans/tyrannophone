package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.OrderService;
import com.nvans.tyrannophone.service.PlanService;
import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"order", "customer"})
public class CartController {

//    @Autowired
//    private CustomerCart customerCart;

    @Autowired
    private PlanService planService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart/add/{planId}")
    public String addPlanToCart(@PathVariable Long planId, @ModelAttribute("cart") Cart cart) {

        PlanDto planDto = planService.getPlan(planId);

        cart.setPlan(planDto);
        cart.setOptions(planDto.getAvailableOptions());

        return "redirect:/home";
    }


    @GetMapping("/cart/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {

        cart.clearCart();

        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String getCart(@ModelAttribute("cart") Cart cart, Model model) {

        if (cart.isCartEmpty()) {
            return "redirect:/home";
        }

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isEmployee = currentUser.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);

        Order order = new Order();
        order.setPlan(cart.getPlan());
        order.setOptions(cart.getPlan().getConnectedOptions());

        model.addAttribute("order", order);


        if (cart.getContract() != null) {
            model.addAttribute("contracts", Collections.singletonList(cart.getContract().getContractNumber()));
            model.addAttribute("customer", customerService.getCustomerByContractNumber(cart.getContract().getContractNumber()));
        }
        else {
            model.addAttribute("contracts", contractService.getAllContracts()
                    .stream().filter(Contract::isActive).map(Contract::getContractNumber).collect(Collectors.toList()));
        }
        model.addAttribute("cart", cart);

        return "cart";
    }

    @PostMapping("/cart")
    public String processOrder(@ModelAttribute Order order,
                               /*@ModelAttribute("cart") CustomerCart customerCart,*/ Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "cart";
        }

        orderService.processOrder(order);
//        customerCart.clearCart();

        model.addAttribute("order", order);
        model.addAttribute("customer", customerService.getCustomerByContractNumber(order.getContract()));

        return "redirect:/cart/invoice";
    }

    @GetMapping("/cart/invoice")
    public String getInvoice() {

        return "cart/invoice";
    }


}
