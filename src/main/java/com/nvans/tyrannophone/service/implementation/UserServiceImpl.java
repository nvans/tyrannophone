package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    GenericDao<User> userDao;

    @Override
    public User getUser(Long id) {

        return userDao.findById(id);
    }
}
