/**
 *
 * Copyright Notice: Openmatics s.r.o -- Confidential and Proprietary
 *
 * All rights reserved.
 * This software is the confidential and proprietary information of Openmatics s.r.o
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Openmatics.
 */
package com.openmatics.omob.clientlogin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * This class provides Spring Security configurations for Client Login Service.
 * @author Kather Basha
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter  {

    /**
     * Customized User Details Service for Spring security implementation.
     */
    @Autowired
    private ClsUserDetailsService userDetailsService;

    /**
     * Attaching User Details Service with authentication configuration.
     * @param auth the authentication manager builder
     * @throws Exception if any
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    /**
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
        .and().httpBasic().and().csrf().disable();

    }

   /* @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("");

    }*/

    /**
     * Password encoder for User Details Service.
     * @return the Password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
