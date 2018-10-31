package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.CustomerInfo;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    GenericDao<User> userDao;

    @Autowired
    CustomerInfo customerInfo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userDao.findByParam("userName", userName.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException("User with name \"" + userName + "\" doesn't exist");
        }

        user.setLastActivityTS(LocalDateTime.now());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new CustomUserPrinciple(
                user.getUserName(), user.getPassword(),
                user.isActive(), user.isActive(),
                user.isActive(), user.isActive(),
                grantedAuthorities, user.getId());
    }


}
