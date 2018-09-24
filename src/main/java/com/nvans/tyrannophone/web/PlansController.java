package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlansController {

    @Autowired
    PlanService planService;

    @GetMapping(value = "/employee/plans")
    public ModelAndView showPlans() {

        ModelAndView modelAndView = new ModelAndView("employee/plans");
        modelAndView.addObject("plans", planService.getAllPlans());

        return modelAndView;
    }

    @GetMapping(value = "/employee/plans/add")
    public String addPlan(Model model) {


        return "employee/plans/add";
    }
}
