package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.security.TyrannophoneUser;

public interface UserPrincipalFacade {

    /**
     * Get current user principle
     *
     * @return current user principle
     */
    TyrannophoneUser currentUser();

}
