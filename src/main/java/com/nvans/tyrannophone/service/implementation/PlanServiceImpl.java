package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.PlanAlreadyExistException;
import com.nvans.tyrannophone.exception.PlanCantBeDeletedException;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    GenericDao<Plan> planDao;

    @Override
    @Transactional(readOnly = true)
    public List<Plan> getAllPlans() {

        return planDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Plan getPlan(Long planId) {

        return planDao.findById(planId);
    }


    @Override
    @Transactional(readOnly = true)
    public Plan getPlan(String planName) {

        return planDao.findByParam("planName", planName);
    }

    @Override
    @Secured({"ROLE_EMPLOYEE"})
    public void addNewPlan(Plan plan) {

        if (planDao.count() != 0) {

            List<Plan> existedPlans = planDao.findAll();

            for (Plan p : existedPlans) {

                if(p.getPlanName().equals(plan.getPlanName())) {

                    throw new PlanAlreadyExistException(
                            "Plan with name " + plan.getPlanName() + " already exists.");
                }
            }
        }

        planDao.create(plan);
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
