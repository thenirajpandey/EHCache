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
package com.openmatics.omob.clientlogin.management.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import com.openmatics.omob.clientlogin.common.EncryptionPassword;
import com.openmatics.omob.clientlogin.dao.ClientMetaDataDAO;
import com.openmatics.omob.clientlogin.dao.UserDAO;
import com.openmatics.omob.clientlogin.management.IUserManagement;
import com.openmatics.omob.clientlogin.management.dto.UserDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import com.openmatics.omob.clientlogin.persistanceobject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.openmatics.omob.clientlogin.common.AuthenticationUtil;


/**
 * {@link IUserManagement} implementation.
 *
 * @author Sumalatha Vadanala
 */
@Service
@CacheConfig(cacheNames={"userCache"})
public class UserManagementImpl implements IUserManagement {

    /** Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementImpl.class);
    /** Regular expression for matching usernames with capturing groups for both the username and client code parts. */
    private static final String REGEXP_USERNAME = "([a-zA-Z0-9]+)@([a-zA-Z0-9]+)";

    /** Minimal username length. */
    private static final int USERNAME_MIN_LENGTH = 6;

    /** Maximal username length. */
    private static final int USERNAME_MAX_LENGTH = 250;

    /** Minimal password length (unencrypted). */
    private static final int PASSWORD_MIN_LENGTH = 6;

    /** Maximal password length (unencrypted). */
    private static final int PASSWORD_MAX_LENGTH = 50;

    /** User Repository for retrieving User information.
     */
    @Autowired
    private  UserDAO userDao;


    /** Client Repository for retrieving Client information.
     */
    @Autowired
    private ClientMetaDataDAO clientMetaDataDAO;


    /** Model Mapper for DTO from/to Entity Conversion.
     */
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    @Caching(evict = {
        	@CacheEvict(cacheNames="userCache", key="#dto.userName"),
        	@CacheEvict(cacheNames="userCache", key="'getAllUsers'")
      		})
    public void createUser(UserDTO dto) {
        LOGGER.trace("Entering into createUser() method");

        // step 1 - validate input data
        validate(dto);

        // step 2 - create entity
        User newUser = createUserEntity(dto);

        // step 3 - insert into the database
        userDao.save(newUser);
        LOGGER.trace("Exiting from createUser() method");
    }

    /**
     * Creates a {@link User} entity and fills it with values from the supplied dto.
     *
     * @param dto the source of user data
     * @return the new {@link User} entity
     */
    private User createUserEntity(UserDTO dto) {
        User newUser = new User();
        newUser.setAdmin(dto.isAdmin());
        newUser.setUserName(dto.getUserName());
        newUser.setPassword(EncryptionPassword.encode(dto.getPassword()));
        newUser.setClientMetaData(clientMetaDataDAO.findByClientCode(parseClientCode(dto.getUserName())));
        return newUser;
    }

    /**
     * Validates user data to be inserted.
     *
     * @param dto the data we want to use
     * @throws ValidationException when validation fails
     */
    private void validate(UserDTO dto) throws ValidationException {

        // username
        // ---------------------------------------------------------
        // empty check
        String newUserName = StringUtils.trim(dto.getUserName());
        if (StringUtils.isBlank(newUserName)) {
            throw new ValidationException("Username must not be empty.");
        }

        if (!newUserName.matches(REGEXP_USERNAME)) {
            throw new ValidationException("Username must match the pattern username@clientCode.");
        }

        if ((newUserName.length() < USERNAME_MIN_LENGTH) || (newUserName.length() > USERNAME_MAX_LENGTH)) {
            throw new ValidationException(
                    "Usernames must be at least " + USERNAME_MIN_LENGTH
                    + " and at most " + USERNAME_MAX_LENGTH + " characters long.");
        }

        // client code check
        User authUser = userDao.findByUserName(AuthenticationUtil.getLoggedInUser());
        String newUsernameClientCode = parseClientCode(newUserName);
        if (!StringUtils.equalsIgnoreCase(authUser.getClientMetaData().getClientCode(), newUsernameClientCode)) {
            throw new ValidationException("Client code part of the username '" + newUserName
                    + "' must match the client code of the authenticated user's client.");
        }

        // does the client exist?
        // we don't need to check (see above - the client exists)

        // check if the user doesn't exist already
        if (userDao.findByUserName(newUserName) != null) {
            throw new ValidationException("User with the username '" + newUserName + "' already exists.");
        }

        // password
        // ---------------------------------------------------------
        String newPassword = StringUtils.trim(dto.getPassword());
        if (StringUtils.isBlank(newPassword)) {
            throw new ValidationException("Password must not be empty.");
        }

        // length check
        if ((newPassword.length() < PASSWORD_MIN_LENGTH) || (newPassword.length() > PASSWORD_MAX_LENGTH)) {
            throw new ValidationException(
                    "Passwords must be at least " + PASSWORD_MIN_LENGTH
                    + " and at most " + PASSWORD_MAX_LENGTH + " characters long.");
        }

        // client code - since it is only allowed to create users under the authenticated user's client
        // we can ignore the client code here and take the client code from the username
        // ---------------------------------------------------------
    }

    /**
     * Parses out the client code part from the username.
     *
     * @param userName the username (username@clientCode format expected)
     * @return the client code part of the supplied username
     */
    private String parseClientCode(String userName) {
        if (StringUtils.isBlank(userName) || !userName.matches(REGEXP_USERNAME)) {
            throw new IllegalArgumentException("The username '" + userName + "' format is not supported.");
        }

        Pattern pat = Pattern.compile(REGEXP_USERNAME);
        Matcher mat = pat.matcher(userName);
        mat.find();
        return mat.group(2);
    }

    @Override
    @Cacheable(key="'getAllUsers'")
    public List<UserDTO> getAllUsers() {
        LOGGER.trace("Entering into getAllUsers method");
        String currentUserName = AuthenticationUtil.getLoggedInUser();
        LOGGER.info("currentUserName = " + currentUserName);
        User userLogin = userDao.findByUserName(currentUserName);
        LOGGER.info("userLogin = " + userLogin);
        Set<User> userDetails = userLogin.getClientMetaData().getUser();
        LOGGER.trace("Exiting from getAllUsers method");
        return userDetails.stream()
                .map(user -> convertToDto(user))
                .collect(Collectors.toList());
    }


    @Override
    @Caching(put = {
    	@CachePut(key="#username"),
    	@CachePut(key="'getAllUsers'")
  		})
    public void updateUser(String username, UserDTO dto) {
        LOGGER.trace("Entering into updateUser() method");
        // Step 0 - fetch user to be updated
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("The username must not be empty.");
        }
        String actualUsername = StringUtils.trim(username);
        User userToUpdate = userDao.findByUserName(actualUsername);
        if (userToUpdate == null) {
            throw new NotFoundException("User with username '" + actualUsername + "' not found.");
        }
        // Step 1 - validate input data
        boolean changed = validateAndCheck(userToUpdate, dto);
        if (changed) {
            // Step 2 - update entity with supplied data
            copyUserData(userToUpdate, dto);
            // Step 3 - save to database
            userDao.save(userToUpdate);
            LOGGER.debug("Updated user '{}'.", actualUsername);

        }
        LOGGER.trace("Exiting from updateUser() method");
    }

    @Override
    @Transactional
    @Caching(evict = {
  		  @CacheEvict( key="#username"),
  		  @CacheEvict( key="'getAllUsers'")
  		})
    @CachePut(cacheNames="userCache", key="username")
    public void deleteUser(String username) {
        User userDetails = userDao.findByUserName(username);
        User loggedInUserDetails = userDao.findByUserName(AuthenticationUtil.getLoggedInUser());

        // checks userDetails exists or not.
        if (userDetails != null) {
            // checks when trying to delete a user from a different client.
            if (StringUtils.equals(userDetails.getClientMetaData().getClientCode(),
                    loggedInUserDetails.getClientMetaData().getClientCode())) {
                // deleting an user by its name.
                LOGGER.info("Deleting user details : " + userDetails.getUserName() + " ,"
                        + userDetails.getClientMetaData().getClientName() + " .");
                userDao.deleteByUserName(username);
                LOGGER.info("User \"" + username + "\" has been deleted.");
            } else {
                throw new ForbiddenException("Delete failed. User \"" + username + "\" does not exist for client \""
                        + userDetails.getClientMetaData().getClientName() + "\".");
            }
        } else {
            throw new BadRequestException("User \"" + username + "\" doesn't exist.");
        }

        LOGGER.trace("Exiting from deleteUser() method.");
    }
    /**
     * Get User details by username.
     */
    @Override
    @Transactional
    @Cacheable(key="#username")
    public UserDTO getUserByName(String username) {
        LOGGER.trace("Entering into getUserByName() method");
        Authentication authentication = AuthenticationUtil.getAuthentication();
        //The name of the loggedin user.
        String loggedInUserName = authentication.getName();

        // Get Logged-in user details.
        User loggedInUserDetails = userDao.findByUserName(loggedInUserName);
        // Get userDetails for the user.
        User userDetails = userDao.findByUserName(username);

        // Check if userDetails is empty or not
        if (userDetails != null) {
            // Check if username is exits under same client as Logged-in user
            if (StringUtils.equals(userDetails.getClientMetaData().getClientCode(),
                    loggedInUserDetails.getClientMetaData().getClientCode())) {
                return transformDTO(userDetails);
            } else {
                throw new ForbiddenException(
                        "User \"" + username + "\" belongs to different Client.");
            }
        } else {
            throw new  NotFoundException("User \"" + username + "\" doesn't exist.");
        }
    }
    /**
     * Transform User Entity to UserDTO.
     * @param user as User Entity object
     * @return UserDTO User Data Transfer Object
     */
    private UserDTO convertToDto(User user) {
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        //setting password value as null to make not visible to the caller
        userDto.setPassword(null);
        LOGGER.debug("userDto = " + userDto);
        return userDto;
    }
    /**
     * Transform User to UserDTO.
     *
     * @param user
     *            user contains user details.
     * @return UserDTO contains user details for Presentation .
     */
    private UserDTO transformDTO(User user) {
        UserDTO dto = new UserDTO();
        String clientCode = user.getClientMetaData().getClientCode();
        boolean admin = user.isAdmin();
        String userName = user.getUserName();
        String password = user.getPassword();

        dto.setAdmin(admin);
        dto.setClientCode(clientCode);
        dto.setPassword(password);
        dto.setUserName(userName);
        return dto;
    }


    /**
     * Copies data from the supplied {@code dto} to the given user entity.
     * The password is only updated when it's not empty.
     *
     * @param userToUpdate the object to copy the data to
     * @param dto the source object
     */
    private void copyUserData(User userToUpdate, UserDTO dto) {
        userToUpdate.setUserName(dto.getUserName());
        if (!StringUtils.isBlank(dto.getPassword())) {
            userToUpdate.setPassword(EncryptionPassword.encode(dto.getPassword()));
        }
        userToUpdate.setAdmin(dto.isAdmin());
    }

    /**
     * Validates data for user update and checks whether some values actually changed.
     *
     * @param userToUpdate the entity from the database we're updating
     * @param dto the data we want to use
     * @return {@code true} when some input data is different from the current state, otherwise {@code false}
     */
    private boolean validateAndCheck(User userToUpdate, UserDTO dto) {
        boolean changed = false;
        // username
        // ---------------------------------------------------------

        // empty check
        String newUserName = StringUtils.trim(dto.getUserName());
        if (StringUtils.isBlank(newUserName)) {
            throw new ValidationException("Username must not be empty.");
        }
        if (!newUserName.matches(REGEXP_USERNAME)) {

            throw new ValidationException("Username must match the pattern username@clientCode.");
        }
        if ((newUserName.length() < USERNAME_MIN_LENGTH) || (newUserName.length() > USERNAME_MAX_LENGTH)) {
            throw new ValidationException(
                    "Usernames must be at least " + USERNAME_MIN_LENGTH
                    + " and at most " + USERNAME_MAX_LENGTH + " characters long.");
        }
        // client code check
        User authUser = userDao.findByUserName(AuthenticationUtil.getLoggedInUser());

        String newUsernameClientCode = parseClientCode(newUserName);

        if (!StringUtils.equalsIgnoreCase(authUser.getClientMetaData().getClientCode(), newUsernameClientCode)) {

            throw new ValidationException("Client code part of the username '" + newUserName

                    + "' must match the client code of the authenticated user's client.");
        }
        // existing username check
        boolean usernameChanged = !StringUtils.equalsIgnoreCase(userToUpdate.getUserName(), newUserName);
        if (usernameChanged) {
            // check if the user doesn't exist already
            if (userDao.findByUserName(newUserName) != null) {
                throw new ValidationException("User with the username '" + newUserName + "' already exists.");
            }

        }

        changed |= usernameChanged;

        // password - the password can be null or empty which means we should ignore it
        // ---------------------------------------------------------
        // or it can have some value and then we'll check
        String newPassword = StringUtils.trim(dto.getPassword());
        if (!StringUtils.isBlank(newPassword)) {
            // length check
            if ((newPassword.length() < PASSWORD_MIN_LENGTH) || (newPassword.length() > PASSWORD_MAX_LENGTH)) {
                throw new ValidationException(
                        "Passwords must be at least " + PASSWORD_MIN_LENGTH
                        + " and at most " + PASSWORD_MAX_LENGTH + " characters long.");
            }
        }


        // when there's some value, we must update the entity

        // (can't compare hashed values because the hash changes every time)
        changed |= !StringUtils.isBlank(newPassword);

        // client code
        // ---------------------------------------------------------
        String newClientCode = StringUtils.trim(dto.getClientCode());
        if (StringUtils.isBlank(newClientCode)) {
            throw new ValidationException("Client code must not be empty.");
        }

        // client code should not change (we probably never want to do this)
        if (!StringUtils.equalsIgnoreCase(
                userToUpdate.getClientMetaData().getClientCode(), newClientCode)) {
            throw new ValidationException("Changing the client code is not supported.");
        }

        // admin flag
        // ---------------------------------------------------------
        changed |= userToUpdate.isAdmin() != dto.isAdmin();
        return changed;
    }

}
