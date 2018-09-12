package com.nvans.tyrannophone.model.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericJpaDao<T extends Serializable> {

    //void setEntityClass(Class<T> entityClass);

    T findById(Long id);

    List<T> findAll();

    void create(T entity);

    void update(T entity);

    void deleteById(Long id);

    void delete(T entity);

}
