package com.nvans.tyrannophone.config;

import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.dao.GenericDaoImpl;
import com.nvans.tyrannophone.model.dto.CustomUserPrinciple;
import com.nvans.tyrannophone.model.entity.*;
import org.springframework.context.annotation.Bean;

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
    public GenericDao<Plan> planDao() {
        return new GenericDaoImpl<>(Plan.class);
    }

    @Bean
    public GenericDao<Customer> customerDao() {
        return new GenericDaoImpl<>(Customer.class);
    }

    @Bean
    public GenericDao<Contract> contractDao() {
        return new GenericDaoImpl<>(Contract.class);
    }

//    @Bean
//    public GenericDao<Details> detailsDao() {
//        return new GenericDaoImpl<>(Details.class);
//    }

}
