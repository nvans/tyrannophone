package com.nvans.tyrannophone.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractGenericDao<T extends Serializable> implements GenericDao<T>{

    private Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractGenericDao() {

    }

    public AbstractGenericDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public List<T> findAll() {

        return entityManager.createQuery(
                "select o from " + type.getSimpleName() + " o", type).getResultList();

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
        entityManager.createQuery("delete o from " + type.getSimpleName() + " where o.id = :id");
    }

    @Override
    public void delete(T entity) {

        entityManager.remove(entity);
    }

    @Override
    public T findByParam(String param, Object value) {
        if (param == null || param.isEmpty()) return null;

        try {
            return entityManager.createQuery(
                    "select o from " + type.getSimpleName() +
                            " o where o." + param + " = :obj", type)
                    .setParameter("obj", value)
                    .getSingleResult();
        }
        catch (NoResultException | IllegalArgumentException ex) {
            return null;
        }
    }

    @Override
    public List<T> findAllByParam(String param, Object value) {

        if (param == null || param.isEmpty()) return null;

        String sn = type.getSimpleName();

        return entityManager.createQuery(
                "select o from " + type.getSimpleName() +
                        " o where o." + param + " = :obj", type)
                .setParameter("obj", value)
                .getResultList();
    }
}
