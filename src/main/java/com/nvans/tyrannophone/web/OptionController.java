package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.OptionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/options")
@Secured("ROLE_EMPLOYEE")
public class OptionController {

    @Autowired
    OptionService optionService;

    @Autowired
    OptionValidationService optionValidationService;

    @GetMapping
    public String getOptions(Model model) {

        model.addAttribute("options", optionService.getAllOptions());

        return "options";
    }

    @GetMapping("/add")
    public String addOption(Model model) {
        model.addAttribute("option", new Option());

        return "options/add";
    }

    @PostMapping("/add")
    public String addOption(@Valid @ModelAttribute Option option, BindingResult result) {

        if (result.hasErrors()) {
            return "options/add";
        }

        optionService.addOption(option);

        return "redirect:/options";
    }

    @GetMapping("/editHierarchy/{optionId}")
    public String editOptionHierarchy(@PathVariable Long optionId, Model model) {

        Option currentOption = optionService.getOptionById(optionId);
        Set<Option> allOptions = optionService.getAllOptions();
        allOptions.remove(currentOption);

        model.addAttribute("option", currentOption);
        model.addAttribute("options", allOptions);

        return "options/editHierarchy";
    }

    @PostMapping("/editHierarchy")
    public String editOptionHierarchy(@ModelAttribute Option option) {

        optionService.editOptionHierarchy(option);

        return "redirect:/options";
    }

    @GetMapping("/editCompatibility/{optionId}")
    public String editCompatibility(@PathVariable Long optionId, Model model) {

        model.addAttribute("option", optionService.getOptionById(optionId));
        model.addAttribute("options", optionService.getCandidatesToIncompatibility(optionId));

        return "options/editCompatibility";
    }

    @PostMapping("/editCompatibility")
    public String editCompatibility(@ModelAttribute Option option, BindingResult result) {

        optionService.updateCompatibility(option);

        return "options/editCompatibility";
    }

}
