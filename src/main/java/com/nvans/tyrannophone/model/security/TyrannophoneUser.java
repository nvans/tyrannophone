package com.nvans.tyrannophone.model.security;


import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class TyrannophoneUser extends User {

    private final Long userId;

    private final boolean isEmployee;

    private final boolean isCustomer;

    // Constructors -->
    public TyrannophoneUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);

        this.userId = userId;
        this.isEmployee = authorities.contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);
        this.isCustomer = authorities.contains(ApplicationAuthorities.CUSTOMER_AUTHORITY);
    }

    public TyrannophoneUser(String username, String password, boolean enabled, boolean accountNonExpired,
                            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.userId = userId;
        this.isEmployee = authorities.contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);
        this.isCustomer = authorities.contains(ApplicationAuthorities.CUSTOMER_AUTHORITY);
    }
    // <-- Constructors

    public Long getUserId() {
        return userId;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public boolean isCustomer() {
        return isCustomer;
    }
}
