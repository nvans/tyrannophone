package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.dto.UserDto;
import com.nvans.tyrannophone.model.dao.UserDao;
import com.nvans.tyrannophone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void registerNewUser(UserDto userDto) {

    }
}
