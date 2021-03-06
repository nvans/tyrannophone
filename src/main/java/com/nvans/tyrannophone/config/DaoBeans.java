package com.nvans.tyrannophone.config;

import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dao.implementation.GenericDaoImpl;
import com.nvans.tyrannophone.model.entity.*;
import com.nvans.tyrannophone.model.security.TyrannophoneUser;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class DaoBeans {

    @Bean
    public GenericDao<User> userDao() {
        return new GenericDaoImpl<>(User.class);
    }

    @Bean
    public GenericDao<Role> roleDao() {
        return new GenericDaoImpl<>(Role.class);
    }

    @Bean
    public GenericDao<BlockDetails> blockDetailsDao() {
        return new GenericDaoImpl<>(BlockDetails.class);
    }

    @Bean
    public GenericDao<Customer> customerDao() {
        return new GenericDaoImpl<>(Customer.class);
    }

    @Bean
    public GenericDao<Contract> contractDao() {
        return new GenericDaoImpl<>(Contract.class);
    }

}
