package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.RoleType;

import java.util.List;

public interface RoleDAO {

    Role findById(Long id);
    Role findByType(RoleType roleType);
    List<Role> findAll();
    void save(Role role);

}
