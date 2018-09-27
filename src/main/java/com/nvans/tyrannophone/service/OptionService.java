package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.entity.Option;

import java.util.Set;

public interface OptionService {

    void addIncompatibleOptions(Option option, Set<Option> incompatibleOptions);

    Set<Option> getAllOptions();

    void addOption(Option option);

    Option getOptionById(Long optionId);

    void editOptionHierarchy(Option option);

    Set<Option> getCandidatesToIncompatibility(Long optionId);

    void updateCompatibility(Option option);

    Option getOptionByName(String name);
}
