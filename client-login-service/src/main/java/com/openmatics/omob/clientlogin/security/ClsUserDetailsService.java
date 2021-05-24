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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.openmatics.omob.clientlogin.dao.UserDAO;

/**
 * This class provides security implementation for Client Login Service.
 *
 * @author Kather Basha
 *
 */
@Service
public class ClsUserDetailsService implements UserDetailsService {

    /**
     *  Logger object for Application logging from this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClsUserDetailsService.class);

    /**
     * Repository to read User Entity Object from Database.
     */
    @Autowired
    private UserDAO userDao;

    /**
     * Custom authentication implementation using Spring security and database.
     *
     * @return authenticated user contains valid user credentials
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("User Name from HTTP request header: " + username);
        com.openmatics.omob.clientlogin.persistanceobject.User user = userDao.findByUserName(username);
        if (user == null) {
            LOGGER.error("User Name Not Found in Database: " + username);
            throw new UsernameNotFoundException("User Name Not Found in Database: " + username);
        }
        LOGGER.debug("User is Admin : " + user.isAdmin());
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList((user.isAdmin() ? "ADMIN" : "USER"));
        return buildUserForAuthentication(user, authorities);

    }

    /**
     * Creating Spring based user credentials from database user details.
     * @param user  database user object
     * @param authorities  list of user roles
     * @return authenticated user contains valid user credentials
     */
    private User buildUserForAuthentication(com.openmatics.omob.clientlogin.persistanceobject.User user,
           List<GrantedAuthority> authorities) {
        return new User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
    }

}
