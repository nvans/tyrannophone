package com.nvans.tyrannophone.model.dao;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public abstract class AbstractJpaDAO<T extends Serializable>
        implements GenericJpaDao<T>  {

    private Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractJpaDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) t;
        type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public List<T> findAll() {

        CriteriaQuery<T> criteria =
                entityManager.getCriteriaBuilder().createQuery(type);

        criteria.select(criteria.from(type));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<T> deleteQuery = criteriaBuilder.createCriteriaDelete(type);

        Root<T> root = deleteQuery.from(type);
        deleteQuery.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(deleteQuery).executeUpdate();

    }

    @Override
    public void delete(T entity) {

        entityManager.remove(entity);
    }
}
