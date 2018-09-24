package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Secured({"ROLE_EMPLOYEE"})
public class OptionValidationServiceImpl implements OptionValidationService {

    @Autowired
    private OptionsGraph optionsGraph;

    @Autowired
    private CycleFinderService cycleFinderService;


    @Override
    @Transactional(readOnly = true)
    public boolean isOptionsHierarchyValid(Set<Option> options) {

        if (options == null) return false;

        boolean b = !cycleFinderService.hasCycle(optionsGraph.getAdj(options));

        return b;
    }

    @Override
    public boolean isIncompatibleOptionsValid(Option option) {

        return false;
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
