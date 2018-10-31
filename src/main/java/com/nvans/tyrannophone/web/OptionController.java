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
import java.util.List;

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

    @GetMapping("/{optionId}")
    public String editOption(@PathVariable Long optionId, Model model) {

        model.addAttribute("option", optionService.getOptionById(optionId));

        return "options/edit";
    }

    @PostMapping("/edit")
    public String editOption(@ModelAttribute Option option, BindingResult result) {

        if (result.hasErrors()) {
            return "options/edit";
        }

        optionService.updateOption(option);

        return "redirect:/options/" + option.getId();
    }

    @GetMapping("/{optionId}/editHierarchy")
    public String editOptionHierarchy(@PathVariable Long optionId, Model model) {

        Option currentOption = optionService.getOptionById(optionId);
        List<Option> allOptions = optionService.getAllOptions();
        allOptions.remove(currentOption);

        model.addAttribute("option", currentOption);
        model.addAttribute("options", allOptions);

        return "options/editHierarchy";
    }

    @PostMapping("/{optionId}/editHierarchy")
    public String editOptionHierarchy(@ModelAttribute Option option) {

        optionService.editOptionHierarchy(option);

        return "redirect:/options/" + option.getId();
    }

    @GetMapping("/{optionId}/editCompatibility")
    public String editCompatibility(@PathVariable Long optionId, Model model) {


        model.addAttribute("option", optionService.getOptionById(optionId));
        model.addAttribute("options", optionService.getAllowableIncompatibleOptionsSet(optionId));

        return "options/editCompatibility";
    }

    @PostMapping("/{optionId}/editCompatibility")
    public String editCompatibility(@ModelAttribute Option option) {

        optionService.updateCompatibility(option);

        return "redirect:/options/" + option.getId();
    }

}
