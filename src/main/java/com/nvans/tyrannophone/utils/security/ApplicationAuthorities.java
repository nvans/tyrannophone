package com.nvans.tyrannophone.utils.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class ApplicationAuthorities {

    public static final SimpleGrantedAuthority CUSTOMER_AUTHORITY = new SimpleGrantedAuthority("ROLE_CUSTOMER");
    public static final SimpleGrantedAuthority EMPLOYEE_AUTHORITY = new SimpleGrantedAuthority("ROLE_EMPLOYEE");

    private ApplicationAuthorities() {

    }

}
