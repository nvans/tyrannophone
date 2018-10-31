package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Option;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OptionService {

    void addIncompatibleOptions(Option option, Set<Option> incompatibleOptions);

    List<Option> getAllOptions();

    List<PlanOptionDto> getAllOptionsDtoList();

    List<PlanOptionDto> getAvailableOptionsForPlan(String planName);

    List<String> getIncompatibleOptionsNames(String optionName);

    Map<Option, Boolean> getAllOptionsMap();

    void addOption(Option option);

    Option getOptionById(Long optionId);

    void editOptionHierarchy(Option option);

    Set<Option> getAllowableIncompatibleOptionsSet(Long optionId);

    void updateCompatibility(Option option);

    Option getOptionByName(String name);

    void updateOption(Option option);
}
