package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.aop.NotifyShowcase;
import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.dto.PlanView;
import com.nvans.tyrannophone.model.dto.PlansCache;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.PlanService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private static final Logger log = Logger.getLogger(PlanServiceImpl.class);

    @Autowired
    PlanDao planDao;

    @Autowired
    OptionDao optionDao;

    @Autowired
    OptionService optionService;

    @Autowired
    OptionsGraph optionsGraph;

    @Autowired
    PlansCache plansCache;

    @Override
    @Transactional(readOnly = true)
    public List<PlanDto> getAllPlans() {

        log.info("Retrieving all plans");

        if (!plansCache.isValid()) {
            List<PlanDto> plans = planDao.findAll()
                    .stream()
                    .map(PlanDto::new)
                    .filter(PlanDto::isConnectionAvailable)
                    .collect(Collectors.toList());

            plansCache.setPlans(plans);
            plansCache.setValid(true);
        }

        log.info("Plans are retrieved");

        return plansCache.getPlans();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanDto getPlan(Long planId) {

        log.info("Getting plan by id " + planId);

        Plan planPO = planDao.findByIdEager(planId);

        PlanDto planDto = new PlanDto(planPO);

        return planDto;
    }


    @Override
    @Transactional(readOnly = true)
    public Plan getPlan(String planName) {

        log.info("Getting plan by name " + planName);

        return planDao.findByName(planName);
    }

    @Override
    public PlanDto getPlanDto(String planName) {

        log.info("Getting plan dto by name " + planName);

        Plan plan = planDao.findByName(planName);

        return new PlanDto(plan);
    }

    @Override
    @NotifyShowcase
    @Secured({"ROLE_EMPLOYEE"})
    public void addPlan(PlanDto planDto) {

        log.info("Adding new plan");
        log.info("Converting");
        // Retrieve persistent plan's object
        Plan plan = new Plan();
        plan.setPlanName(planDto.getPlanName());                      // name
        plan.setConnectionPrice(planDto.getConnectionPrice());        // connection price
        plan.setMonthlyPrice(planDto.getConnectedOptions()            // monthly price
                .stream().mapToInt(PlanOptionDto::getPrice).sum());
        plan.setConnectionAvailable(planDto.isConnectionAvailable()); // availability
        plan.setDescription(planDto.getDescription());                // description

        // Add parent options
        log.info("Adding parent options");
        Set<Option> availableOptions = new HashSet<>();
        for(PlanOptionDto opt : planDto.getAvailableOptions()) {
            Option optPO = optionService.getOptionById(opt.getId());
            availableOptions.add(optPO);
            availableOptions.addAll(optionsGraph.getParents(optPO));
        }

        // Set connected options
        log.info("Setting connection options");
        List<Long> connectedOptionsIds = planDto.getConnectedOptions()
                .stream()
                .map(PlanOptionDto::getId)
                .collect(Collectors.toList());

        Map<Option, Boolean> optionsMap = new HashMap<>(availableOptions.size());
        for(Option option : availableOptions) {
            optionsMap.put(option, connectedOptionsIds.contains(option.getId()));
        }
        plan.setAvailableOptions(optionsMap);

        log.info("Persisting");
        planDao.save(plan);

        plansCache.setValid(false);
    }

    @Override
    @NotifyShowcase
    @Secured({"ROLE_EMPLOYEE"})
    public void updatePlan(PlanDto planDto) {

        log.info("Updating plan");

        if(!(planDto.getAvailableOptions().containsAll(planDto.getConnectedOptions()))) {
            throw new TyrannophoneException("Option marked as 'connected' must be 'available' too.");
        }

        // Retrieve persistent plan's object
        Plan planPO = planDao.findById(planDto.getId());
        planPO.setPlanName(planDto.getPlanName());                      // name
        planPO.setConnectionPrice(planDto.getConnectionPrice());        // connection price
        planPO.setMonthlyPrice(planDto.getConnectedOptions()            // monthly price
                .stream().mapToInt(PlanOptionDto::getPrice).sum());
        planPO.setConnectionAvailable(planDto.isConnectionAvailable()); // availability
        planPO.setDescription(planDto.getDescription());                // description

        // Add parent options
        Set<Option> availableOptions = new HashSet<>();
        for(PlanOptionDto opt : planDto.getAvailableOptions()) {

            Option optPO = optionService.getOptionById(opt.getId());
            availableOptions.add(optPO);
            availableOptions.addAll(optionsGraph.getParents(optPO));
        }

        List<Long> connectedOptionsIds = planDto.getConnectedOptions()
                .stream()
                .map(PlanOptionDto::getId)
                .collect(Collectors.toList());

        Map<Option, Boolean> optionsMap = new HashMap<>(availableOptions.size());
        for(Option option : availableOptions) {
            optionsMap.put(option, connectedOptionsIds.contains(option.getId()));
        }

        planPO.setAvailableOptions(optionsMap);


        plansCache.setValid(false);
    }

    @Override
    @NotifyShowcase
    @Secured({"ROLE_EMPLOYEE"})
    public void deletePlan(Long planId) {

        log.info("Deleting plan " + planId);

        Plan plan = planDao.findById(planId);

        Set<Contract> planContracts = plan.getContracts();

        if (!(planContracts.isEmpty())) {
            plan.setConnectionAvailable(false);
        }
        else {
            planDao.delete(plan);
        }

        plansCache.setValid(false);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void changeAvailableOptions(Long planId, Set<Option> changedOptions) {


    }

    @Override
    public List<PlanView> getAllPlansViews() {

        List<Plan> plans = planDao.findAll();

        return plans.stream()
                .filter(Plan::isConnectionAvailable)
                .map(PlanView::new)
                .collect(Collectors.toList());
    }

    @Override
    public PlanView getPlanView(Long planId) {

        Plan plan = planDao.findById(planId);

        return new PlanView(plan);
    }
}
