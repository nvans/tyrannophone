package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Plan;

public interface PlanDao extends GenericDao<Plan> {

    Plan findByIdEager(Long planId);

    Plan findByName(String planName);
}
