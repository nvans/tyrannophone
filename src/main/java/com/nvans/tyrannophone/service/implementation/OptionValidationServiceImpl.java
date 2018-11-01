package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionValidationServiceImpl implements OptionValidationService {

    private final OptionDao optionDao;
    private final OptionsGraph optionsGraph;
    private final CycleFinderService cycleFinderService;

    @Autowired
    public OptionValidationServiceImpl(OptionDao optionDao, OptionsGraph optionsGraph, CycleFinderService cycleFinderService) {
        this.optionDao = optionDao;
        this.optionsGraph = optionsGraph;
        this.cycleFinderService = cycleFinderService;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isOptionsHierarchyValid(Set<Option> options) {

        if (options == null) return false;

        return !(cycleFinderService.hasCycle(optionsGraph.getAdj(options)));
    }

    @Override
    public boolean hasValidHierarchy(Option option) {

        if (option == null) return false;

        if (option.equals(option.getParentOption())
                || option.getChildOptions().contains(option)
                || option.getChildOptions().contains(option.getParentOption())) {

            return false;
        }

        // Get persistent object
        Option optionPO = optionDao.findById(option.getId());
        // Set parent and children
        Option parent = (option.getParentOption() != null) ? optionDao.findById(option.getParentOption().getId()) : null;
        optionPO.setParentOption(parent);
        optionPO.setChildOptions(option.getChildOptions()
                .stream().map(o -> optionDao.findById(o.getId())).collect(Collectors.toSet()));

        Set<Option> optionHierarchy = new HashSet<>();
        optionHierarchy.add(optionPO);

        try {
            optionHierarchy.addAll(optionsGraph.getChildren(optionPO));
            optionHierarchy.addAll(optionsGraph.getParents(optionPO));
        }
        catch (TyrannophoneException ignored) {
            return false;
        }

        return !(cycleFinderService.hasCycle(optionHierarchy));
    }

    @Override
    public boolean areOptionsCompatible(Set<Option> options) {

        for (Option optI : options) {
            for (Option optJ : options) {

                if (optI.equals(optJ)) {
                    continue;
                }

                if(optI.getIncompatibleOptions().contains(optJ)) {
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public boolean haveParentChildRelation(Option opt1, Option opt2) {

        Set<Option> option1Hierarchy = new HashSet<>();
        Set<Option> option2Hierarchy = new HashSet<>();

        Option parent = opt1;

        while (parent != null) {

            option1Hierarchy.add(parent);

            parent = parent.getParentOption();
        }

        parent = opt2;

        while (parent != null) {

            option2Hierarchy.add(parent);

            parent = parent.getParentOption();
        }

        return option1Hierarchy.contains(opt2) || option2Hierarchy.contains(opt1);

    }


}
