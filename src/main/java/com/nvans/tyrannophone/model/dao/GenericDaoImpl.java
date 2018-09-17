package com.nvans.tyrannophone.model.dao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class GenericDaoImpl<T extends Serializable>
        extends AbstractGenericDao<T> {

    public GenericDaoImpl() {}

    public GenericDaoImpl(Class<T> type) {
        super(type);
    }
}
