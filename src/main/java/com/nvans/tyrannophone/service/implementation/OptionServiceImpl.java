package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.aop.NotifyShowcase;
import com.nvans.tyrannophone.exception.OptionCantBeAddedException;
import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.OptionValidationService;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    private static final Logger log = Logger.getLogger(OptionServiceImpl.class);

    private final PlanDao planDao;
    private final OptionDao optionDao;
    private final OptionValidationService optionValidationService;
    private final OptionsGraph optionsGraph;


    @Autowired
    public OptionServiceImpl(PlanDao planDao, OptionDao optionDao, OptionValidationService optionValidationService, OptionsGraph optionsGraph) {
        this.planDao = planDao;
        this.optionDao = optionDao;
        this.optionValidationService = optionValidationService;
        this.optionsGraph = optionsGraph;
    }

    @Override
    @NotifyShowcase
    public void addIncompatibleOptions(Option option, Set<Option> incompatibleOptions) {

        log.info("Add incompatible options for option [" + option.getName() + "]");

        // Retrieve branch of options graph with current option and it's children.
        Set<Option> optionWithChildren = optionsGraph.getChildren(option);

        log.info("Check consistency for incompatible options");
        Set<Option> incompatibleOptionsWithChildren = new HashSet<>();

        for(Option incompatibleOpt : incompatibleOptions) {
            if(optionValidationService.haveParentChildRelation(option, incompatibleOpt)) {

                log.warn("Incompatible options for option [" + option.getName() + "] have parent-child relation.");

                throw new OptionCantBeAddedException("Options with the parent-child relation can't be incompatible!");
            }

            incompatibleOptionsWithChildren.addAll(optionsGraph.getChildren(incompatibleOpt));
        }

        // Add for current option branch all retrieved incompatible options
        for (Option opt : optionWithChildren) {
            opt.setIncompatibleOptions(incompatibleOptionsWithChildren);
        }

        // and otherwise
        for (Option opt : incompatibleOptionsWithChildren) {
            opt.setIncompatibleOptions(optionWithChildren);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Option> getAllOptions() {

        log.info("Retrieving all options");

        List<Option> options = new ArrayList<>(optionDao.findAll());
        options.sort(Comparator.comparing(Option::getName));

        return options;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanOptionDto> getAllOptionsDtoList() {

        log.info("Retrieving all options dtos");

        return optionDao.findAll().stream()
                .filter(Option::isConnectionAvailable)
                .map(PlanOptionDto::new)
                .sorted(Comparator.comparing(PlanOptionDto::getName))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlanOptionDto> getAvailableOptionsForPlan(String planName) {

        log.info("Retrieving available options for plan [" + planName + "]");

        Plan plan = planDao.findByName(planName);

        List<PlanOptionDto> availableOptions = new ArrayList<>();

        for(Map.Entry<Option, Boolean> pair : plan.getAvailableOptions().entrySet()) {
            availableOptions.add(new PlanOptionDto(pair.getKey(), pair.getValue()));
        }

        availableOptions.sort(Comparator.comparing(PlanOptionDto::getName));

        return availableOptions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getIncompatibleOptionsNames(String optionName) {

        log.info("Retrieving incompatible options for option [" + optionName + "]");

        Option option = optionDao.findByName(optionName);

        return option.getIncompatibleOptions()
                .stream()
                .map(Option::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Option, Boolean> getAllOptionsMap() {

        log.info("Retrieving all options");

        List<Option> options = optionDao.findAll();

        return options
                .stream()
                .collect(Collectors.toMap((option) -> option, (o) -> false));
    }

    @Override
    public void addOption(Option option) {

        log.info("Adding option [" + option.getName() + "]");

        option.setName(option.getName().trim());
        option.setParentOption(null);

        optionDao.save(option);
    }

    @Override
    @Transactional(readOnly = true)
    public Option getOptionById(Long optionId) {

        log.info("Getting option by id [" + optionId + "]");

        Option option = optionDao.findByIdEager(optionId);

        return option;
    }

    @Override
    public void editOptionHierarchy(Option option) {

        log.info("Editing option hierarchy for option [" + option.getName() + "]");

        if(!(optionValidationService.hasValidHierarchy(option))) {
            throw new TyrannophoneException("Invalid options hierarchy");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Set<Option> getAllowableIncompatibleOptionsSet(Long optionId) {

        log.info("Retrieving incompatible options for option [" + optionId + "]");

        Option optionPO = optionDao.findById(optionId);

        Set<Option> candidates  = new HashSet<>(optionDao.findAll());

        Set<Option> optionsToExclude = new HashSet<>();

        optionsToExclude.addAll(optionsGraph.getParents(optionPO));
        optionsToExclude.addAll(optionsGraph.getChildren(optionPO));
        optionsToExclude.add(optionPO);

        candidates.removeAll(optionsToExclude);

        return candidates;
    }

    @Override
    public void updateCompatibility(Option option) {

        log.info("Updating options compatibility");

        Option optionPO = optionDao.findByIdEager(option.getId());

        // getting persistent objects for each new incompatible option
        // and put it to set.
        Set<Option> newIncompatibleOptions = new HashSet<>();
        newIncompatibleOptions.addAll(option.getIncompatibleOptions());

        option.getIncompatibleOptions().forEach(
                opt -> newIncompatibleOptions.addAll(optionsGraph.getChildren(opt))
        );

        // getting prior incompatible options for current option
        Set<Option> oldIncompatibleOptions = new HashSet<>();
        optionPO.getIncompatibleOptions().forEach(
                opt -> oldIncompatibleOptions.addAll(optionsGraph.getChildren(opt))
        );

        // *** Consistency ***
        // 1. Remove current option from other incompatible set if was switched off
        for(Option storedOption : oldIncompatibleOptions) {
            if (!(newIncompatibleOptions.contains(storedOption))) {
                storedOption.getIncompatibleOptions().remove(optionPO);
            }
        }
        // 2. Add for each incompatible option this option as incompatible
        newIncompatibleOptions.forEach(opt -> opt.getIncompatibleOptions().add(optionPO));

        optionPO.setIncompatibleOptions(newIncompatibleOptions);
    }

    @Override
    @Transactional(readOnly = true)
    public Option getOptionByName(String name) {

        log.info("Retrieving option by name [" + name + "]");

        return optionDao.findByNameEager(name);
    }

    @Override
    public void updateOption(Option option) {

//        optionValidationService.isOptionsHierarchyValid()
    }


}
