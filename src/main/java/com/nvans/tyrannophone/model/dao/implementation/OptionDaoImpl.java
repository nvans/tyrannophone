package com.nvans.tyrannophone.model.dao.implementation;

import com.nvans.tyrannophone.model.dao.AbstractGenericDao;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OptionDaoImpl extends AbstractGenericDao<Option> implements OptionDao {

    public OptionDaoImpl() {
        super(Option.class);
    }

//    @Override
//    public Option findById(Long id) {
//
//        TypedQuery<Option> query = entityManager.createQuery(
//                "SELECT o FROM Option o " +
//                "LEFT JOIN FETCH o.childOptions " +
//                "LEFT JOIN FETCH o.parentOption " +
//                "LEFT JOIN FETCH o.incompatibleOptions " +
//                "WHERE o.id = :id", Option.class);
//        query.setParameter("id", id);
//
//        return query.getSingleResult();
//    }

    @Override
    public Option findByIdEager(Long optionId) {

        TypedQuery<Option> query = entityManager.createQuery(
                "SELECT o FROM Option o " +
                "LEFT JOIN FETCH o.childOptions " +
                "LEFT JOIN FETCH o.parentOption " +
                "LEFT JOIN FETCH o.incompatibleOptions " +
                "WHERE o.id = :id", Option.class);
        query.setParameter("id", optionId);

        List<Option> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public Option findByName(String optionName) {

        return findByParam("name", optionName);
    }

    @Override
    public Option findByNameEager(String optionName) {

        TypedQuery<Option> query = entityManager.createQuery(
                "SELECT o FROM Option o " +
                "LEFT JOIN FETCH o.childOptions " +
                "LEFT JOIN FETCH o.parentOption " +
                "LEFT JOIN FETCH o.incompatibleOptions " +
                "WHERE o.name = :name", Option.class);
        query.setParameter("name", optionName);

        List<Option> result = query.getResultList();

        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }
}
