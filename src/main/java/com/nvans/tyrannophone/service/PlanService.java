package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanView;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;

import java.util.List;
import java.util.Set;

public interface PlanService {

    /**
     * The method retrieves all plans.
     *
     * @return list of plans
     */
    List<PlanDto> getAllPlans();

    /**
     * The method retrieves a single plan by id.
     *
     * @param planId - id of the plan
     *
     * @return plan.
     */
    PlanDto getPlan(Long planId);

    /**
     * The method retrieves a single plan by name.
     *
     * @param planName - name of the plan
     *
     * @return plan.
     */
    Plan getPlan(String planName);

    PlanDto getPlanDto(String planName);

    /**
     * The method adds new plan to the system.
     *
     * @param plan - plan object
     */
    void addPlan(PlanDto plan);

    void updatePlan(PlanDto planDto);

    /**
     * The method deletes an existed plan from the system by id.
     *
     * @param planId - identifier of the plan
     */
    void deletePlan(Long planId);

    /**
     * The method modifies the available options list
     * for the plan.
     *
     * @param planId - identifier of the plan to modify
     * @param changedOptions - new options set
     */
    void changeAvailableOptions(Long planId, Set<Option> changedOptions);


    List<PlanView> getAllPlansViews();

    PlanView getPlanView(Long planId);
}
