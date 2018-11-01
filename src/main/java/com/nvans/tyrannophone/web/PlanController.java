package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/plans")
@SessionAttributes("options")
public class PlanController {

    @Autowired
    PlanService planService;

    @Autowired
    OptionService optionService;

    @GetMapping
    public ModelAndView showPlans() {

        ModelAndView modelAndView = new ModelAndView("plans");
        modelAndView.addObject("plans", planService.getAllPlans());

        return modelAndView;
    }

    @GetMapping(value = "/add")
    @Secured("ROLE_EMPLOYEE")
    public String addPlan(Model model) {

        model.addAttribute("plan", new PlanDto());
        model.addAttribute("options", optionService.getAllOptionsDtoList());

        return "plans/add";
    }

    @PostMapping("/add")
    @Secured("ROLE_EMPLOYEE")
    public String addPlan(@Valid @ModelAttribute("plan") PlanDto plan, BindingResult result, SessionStatus sessionStatus) {

        if (result.hasErrors()) {
            return "plans/add";
        }

        planService.addPlan(plan);
        sessionStatus.setComplete();

        return "redirect:/plans";
    }

    @GetMapping("/{planId}")
    @Secured("ROLE_EMPLOYEE")
    public String editPlan(@PathVariable Long planId, Model model) {

        model.addAttribute("plan", planService.getPlan(planId));
        model.addAttribute("options", optionService.getAllOptionsDtoList());

        return "plans/edit";
    }

    @PostMapping("/edit")
    @Secured("ROLE_EMPLOYEE")
    public String editPlan(@ModelAttribute("plan") PlanDto plan, BindingResult result, SessionStatus sessionStatus) {

        if (result.hasErrors()) {
            return "plans/edit";
        }

        planService.updatePlan(plan);
        sessionStatus.setComplete();

        return "redirect:/plans";
    }

    @GetMapping("/delete/{planId}")
    @Secured("ROLE_EMPLOYEE")
    public String deletePlan(@PathVariable Long planId) {

        planService.deletePlan(planId);

        return "redirect:/plans";
    }


}
