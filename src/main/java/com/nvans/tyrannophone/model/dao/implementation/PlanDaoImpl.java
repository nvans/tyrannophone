package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.entity.Plan;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlanDaoImpl extends AbstractGenericDao<Plan> implements PlanDao {

    private final static Logger LOGGER = Logger.getLogger(PlanDaoImpl.class);

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

        List<Plan> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
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

        LOGGER.info("Trying to find plan by name '" + planName + "'.");

        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p " +
                "LEFT JOIN FETCH p.availableOptions " +
                "WHERE p.planName = :planName", Plan.class);
        query.setParameter("planName", planName);

        List<Plan> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }
}
