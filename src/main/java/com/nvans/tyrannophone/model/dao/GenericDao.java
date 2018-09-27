package com.nvans.tyrannophone.model.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T extends Serializable> {

    T findById(Long id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void deleteById(Long id);

    void delete(T entity);

    T findByParam(String param, Object value);

    List<T> findAllByParam(String param, Object value);

    long count();

}
