package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.nvans.tyrannophone.web"})
public class GlobalControllerAdvice {

    @Autowired
    private OptionService optionService;

    @ExceptionHandler(TyrannophoneException.class)
    public ModelAndView exceptionHandler(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", ex.getMessage());

        return modelAndView;

    }


}
