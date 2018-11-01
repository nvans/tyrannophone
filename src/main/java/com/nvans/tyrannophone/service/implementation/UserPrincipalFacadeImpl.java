package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.security.TyrannophoneUser;
import com.nvans.tyrannophone.service.UserPrincipalFacade;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalFacadeImpl implements UserPrincipalFacade {

    @Override
    public TyrannophoneUser currentUser() {

        return (TyrannophoneUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
