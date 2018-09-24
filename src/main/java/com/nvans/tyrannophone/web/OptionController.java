package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.OptionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class OptionController {

    @Autowired
    OptionService optionService;

    @Autowired
    OptionValidationService optionValidationService;

    @GetMapping("/employee/options")
    public String showOptions(Model model) {

        model.addAttribute("options", optionService.getAllOptions());

        return "employee/options/options";
    }

    @GetMapping("/employee/options/add")
    public String addOption(Model model) {
        model.addAttribute("option", new Option());

        return "employee/options/add";
    }

    @PostMapping("/employee/options/add")
    public String addOption(@Valid @ModelAttribute Option option, BindingResult result) {

        if (result.hasErrors()) {
            return "employee/options/add";
        }

        optionService.addOption(option);

        return "redirect:/employee/options";
    }

    @GetMapping("/employee/options/editHierarchy/{optionId}")
    public String editOptionHierarchy(@PathVariable Long optionId, Model model) {

        Option currentOption = optionService.getOptionById(optionId);
        Set<Option> allOptions = optionService.getAllOptions();
        allOptions.remove(currentOption);

        model.addAttribute("option", currentOption);
        model.addAttribute("options", allOptions);

        return "employee/options/editHierarchy";
    }

    @PostMapping("/employee/options/editHierarchy")
    public String editOptionHierarchy(@ModelAttribute Option option) {

        optionService.editOptionHierarchy(option);

        return "redirect:/employee/options";
    }

}
