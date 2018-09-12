package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.User;

import java.util.List;

public interface UserDao extends GenericJpaDao<User> {

    User findByUserName(String userName);

    User findByEmail(String email);


}
