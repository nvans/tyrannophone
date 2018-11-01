package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.cart.OrderCart;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.security.TyrannophoneUser;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.CustomerService;
import com.nvans.tyrannophone.service.OrderService;
import com.nvans.tyrannophone.service.PlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"order", "customer"})
public class CartController {

    private static final Logger log = Logger.getLogger(CartController.class);

    @Autowired
    private OrderCart orderCart;

    @Autowired
    private PlanService planService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    // Todo: finish it
    @GetMapping("/cart/add/{planId}")
    public String addPlanToCart(@PathVariable Long planId, @ModelAttribute("cart") Cart cart) {


        PlanDto planDto = planService.getPlan(planId);

        TyrannophoneUser currentUser = (TyrannophoneUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerDto customerDto = new CustomerDto(customerService.getCustomerDetails());

        Long contN = customerDto.getContracts().isEmpty() ?null : customerDto.getContracts().get(0);
        if (contN != null) {
            orderService.createPlanOrder(customerDto, contractService.getContractDtoByNumber(contN));
        }

        return "redirect:/home";
    }


    @GetMapping("/cart/clear")
    public String clearCart() {

        orderCart.clear();

        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {

        model.addAttribute("orderCart", orderCart);

        return "cart";
    }


    @GetMapping("/cart/processAll")
    public String processAll() {

        orderCart.getOrders().forEach(order -> {
            try {
                orderService.processOrder(order);
            }
            catch (TyrannophoneException exc) {
                log.warn(exc.getMessage());
            }
        });

        return "redirect:/home";
    }


    @GetMapping("/cart/{orderIndex}/process")
    public String processOrder(@PathVariable Integer orderIndex, Model model) {

        Order order = orderCart.getOrder(orderIndex - 1);

        orderService.processOrder(order);

        model.addAttribute("order", order);

        return "/cart/invoice";
    }

    @GetMapping("/cart/{orderIndex}/delete")
    public String deleteOrder(@PathVariable Integer orderIndex) {

        orderCart.deleteOrder(orderIndex - 1);

        return "redirect:/cart";
    }

    @GetMapping("/cart/{orderIndex}/invoice")
    public String getInvoice(@PathVariable Integer orderIndex, Model model) {

        model.addAttribute("order", orderCart.getOrder(orderIndex - 1));

        return "cart/invoice";
    }


}
