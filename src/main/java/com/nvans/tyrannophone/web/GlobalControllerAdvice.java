package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.CustomerInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice(basePackages = {"com.nvans.tyrannophone.web"})
public class GlobalControllerAdvice {

    private static final Logger log = Logger.getLogger(GlobalControllerAdvice.class);

    @Autowired
    private Cart cart;

    @Autowired
    private CustomerInfo customerInfo;

    @ExceptionHandler(TyrannophoneException.class)
    public ModelAndView exceptionHandler(Exception ex) {

        log.warn(ex.getMessage());

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", ex.getMessage());

        return modelAndView;

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return "404";
    }


    @ModelAttribute("cart")
    public Cart customerCart() {
        return cart;
    }

    @ModelAttribute("customerInfo")
    public CustomerInfo customerInfo() {
        return customerInfo;
    }

}
