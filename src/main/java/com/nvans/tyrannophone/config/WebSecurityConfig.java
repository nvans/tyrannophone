package com.nvans.tyrannophone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin**").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/contracts/**").hasAnyRole("CUSTOMER", "EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/contracts/add").hasRole("EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/options/**").hasRole("EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/plans/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/options/**").hasRole("EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/customers/**").hasRole("EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/cart/**").hasAnyRole("CUSTOMER", "EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/api/contracts/**").hasAnyRole("CUSTOMER", "EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/api/plans/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/*").permitAll()
                .and()
                .authorizeRequests().antMatchers("/registration").anonymous()
                .and()
                .authorizeRequests().antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/home").failureForwardUrl("/home/?error=true")
                                        .successForwardUrl("/home?success=true")
                    .permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable()
                .sessionManagement().maximumSessions(-1).sessionRegistry(sessionRegistry()).expiredUrl("/home");

    }




}
