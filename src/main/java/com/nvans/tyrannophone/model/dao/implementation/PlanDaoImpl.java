package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.entity.Plan;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlanDaoImpl extends AbstractGenericDao<Plan> implements PlanDao {

    public PlanDaoImpl() {
        super(Plan.class);
    }

    @Override
    public Plan findByIdEager(Long planId) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p " +
                        "LEFT JOIN FETCH p.availableOptions " +
                        "WHERE p.id = :id", Plan.class);
        query.setParameter("id", planId);

        return query.getSingleResult();
    }

    @Override
    public List<Plan> findAll() {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Plan p " +
                "LEFT JOIN FETCH p.availableOptions", Plan.class);

        return query.getResultList();
    }

    @Override
    public Plan findByName(String planName) {

        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p " +
                "LEFT JOIN FETCH p.availableOptions " +
                "WHERE p.planName = :planName", Plan.class);
        query.setParameter("planName", planName);

        return query.getSingleResult();
    }
}
