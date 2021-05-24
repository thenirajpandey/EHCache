package com.openmatics.omob.clientlogin.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openmatics.omob.clientlogin.persistanceobject.User;

/**
 * Repository for User data implemented using Spring Data JPA.
 *
 * @author Sumalatha Vadanala
 *
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    /**
     * method to find user by name.
     *
     * @param userName
     *            input parameter
     * @return User
     */
    User findByUserName(String userName);

    /**
     * Deletes a user with the supplied username.
     *
     * @param userName
     *            the username of the user that we want to delete
     */
    @Modifying
    @Transactional
    @Query("delete from User u where u.userName = ?1")
    void deleteByUserName(String userName);

}
