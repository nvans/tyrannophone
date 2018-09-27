package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.PlanCantBeDeletedException;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.PlanService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanDao planDao;

    @Autowired
    OptionDao optionDao;

    @Autowired
    OptionService optionService;

    @Autowired
    OptionsGraph optionsGraph;

    @Override
    @Transactional(readOnly = true)
    public List<Plan> getAllPlans() {

        return planDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Plan getPlan(Long planId) {

        return planDao.findByIdEager(planId);
    }


    @Override
    @Transactional(readOnly = true)
    public Plan getPlan(String planName) {

        return planDao.findByName(planName);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void addPlan(Plan plan) {

        Set<Option> availableOptions = new HashSet<>();

        for(Option opt : plan.getAvailableOptions()) {
            availableOptions.addAll(optionsGraph.getAllParents(opt));
        }

        availableOptions.addAll(plan.getAvailableOptions());
        plan.setAvailableOptions(availableOptions);

        planDao.save(plan);
    }

    @Override
    public void updatePlan(Plan plan) {

        Plan planPO = planDao.findById(plan.getId());

        Set<Option> availableOptions = new HashSet<>();

        for(Option opt : plan.getAvailableOptions()) {
            availableOptions.addAll(optionsGraph.getAllParents(opt));
        }

        availableOptions.addAll(plan.getAvailableOptions());

        planPO.setAvailableOptions(availableOptions);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void deletePlan(Long planId) {

        Plan plan = planDao.findById(planId);

        Set<Contract> planContracts = plan.getContracts();

        if (! planContracts.isEmpty()) {

            throw new PlanCantBeDeletedException(
                    "The plan '" + plan.getPlanName() + "' has a contracts.");
        }
        
        planDao.delete(plan);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void changeAvailableOptions(Long planId, Set<Option> changedOptions) {


    }
}
