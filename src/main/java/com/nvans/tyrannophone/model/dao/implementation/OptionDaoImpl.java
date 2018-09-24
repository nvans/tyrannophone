package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class OptionDaoImpl extends AbstractGenericDao<Option> implements OptionDao {

    public OptionDaoImpl() {
        super(Option.class);
    }

    @Override
    public Option findById(Long id) {

        TypedQuery<Option> query = entityManager.createQuery(
                "SELECT o FROM Option o " +
                "LEFT JOIN FETCH o.childOptions " +
                "LEFT JOIN FETCH o.parentOption " +
                "WHERE o.id = :id", Option.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }
}
