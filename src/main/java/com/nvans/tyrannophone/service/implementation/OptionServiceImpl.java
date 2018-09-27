package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.OptionCantBeAddedException;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private OptionValidationService optionValidationService;

    @Autowired
    OptionsGraph optionsGraph;

    @Autowired
    CycleFinderService cycleFinderService;

    @Override
    public void addIncompatibleOptions(Option option, Set<Option> incompatibleOptions) {

        Set<Option> optionChildren = optionsGraph.getAllChildren(option);
        Set<Option> incompatibleOptionsChildren = new HashSet<>();

        for(Option incompatibleOpt : incompatibleOptions) {

            if(optionValidationService.haveParentChildRelation(option, incompatibleOpt)) {
                throw new OptionCantBeAddedException("Options with the parent-child relation can't be incompatible!");
            }

            incompatibleOptionsChildren.addAll(optionsGraph.getAllChildren(incompatibleOpt));
        }

        for (Option opt : optionChildren) {
            opt.setIncompatibleOptions(incompatibleOptionsChildren);
        }

        for (Option opt : incompatibleOptionsChildren) {
            opt.setIncompatibleOptions(optionChildren);
        }

    }

    @Override
    public Set<Option> getAllOptions() {

        return new HashSet<>(optionDao.findAll());
    }

    @Override
    public void addOption(Option option) {

        option.setName(option.getName().trim());

        optionDao.save(option);
    }

    @Override
    public Option getOptionById(Long optionId) {

        return optionDao.findById(optionId);
    }

    @Override
    public void editOptionHierarchy(Option option) {

        Set<Option> optionSet = new HashSet<>(optionDao.findAll());

        if (option.getChildOptions().isEmpty()) {

            for (Option opt : optionSet) {
                if (option.equals(opt.getParentOption())) {
                    opt.setParentOption(null);
                }
            }

        }

        for (Option opt : option.getChildOptions()) {
            opt.setParentOption(option);
            optionSet.remove(opt);
            optionSet.add(opt);
        }

        optionSet.remove(option);
        optionSet.add(option);

        if(optionValidationService.isOptionsHierarchyValid(optionSet)) {
            for(Option opt : optionSet) {
                optionDao.update(opt);
            }
        }
        else {
            throw new OptionCantBeAddedException("Invalid options hierarchy.");
        }


    }

    @Override
    @Transactional(readOnly = true)
    public Set<Option> getCandidatesToIncompatibility(Long optionId) {

        Option optionPO = optionDao.findById(optionId);

        Set<Option> candidates  = new HashSet<>(optionDao.findAll());

        Set<Option> optionsToExclude = new HashSet<>();

        optionsToExclude.addAll(optionsGraph.getAllParents(optionPO));
        optionsToExclude.addAll(optionsGraph.getAllChildren(optionPO));
        optionsToExclude.add(optionPO);

        candidates.removeAll(optionsToExclude);

        return candidates;
    }

    @Override
    public void updateCompatibility(Option option) {

        Set<Option> tmp = null;

        for (Option opt : option.getIncompatibleOptions()) {
            tmp = opt.getIncompatibleOptions();
            tmp.add(option);
            opt.setIncompatibleOptions(tmp);
        }

        optionDao.update(option);
    }

    @Override
    public Option getOptionByName(String name) {

        return optionDao.findByParam("name", name);
    }


}
