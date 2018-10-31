package com.nvans.tyrannophone.service;


import com.nvans.tyrannophone.model.entity.User;

public interface UserService {

    User getUser(Long id);

    User getUserByEmail(String email);
}
