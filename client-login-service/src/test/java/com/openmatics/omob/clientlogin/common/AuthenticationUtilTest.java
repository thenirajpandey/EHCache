package com.openmatics.omob.clientlogin.common;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * This class checks the method functionalities in the AuthenticalUtil.java Class.
 *
 * @author RR00503779 (rr00503779@techmahindra.com)
 *
 */

public class AuthenticationUtilTest {

    /**
     * Checks the code to throw
     * java.lang.IllegalStateException
     * if there's no authenticated user (is anonymous user).
     */
    @Test(expected = IllegalStateException.class)
    public void testIsUserAnAnonymousUser() {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("GUEST", "USERNAME",
        AuthorityUtils.createAuthorityList("USER")));
        AuthenticationUtil.getLoggedInUser();

    }

  /**
   *Checks getLoggedInUser method if authentication is null.
   */
   @Test(expected = IllegalStateException.class)
     public void isAuthenticationNull() {
       SecurityContextHolder.getContext().setAuthentication(null);
        AuthenticationUtil.getLoggedInUser();

     }

    /**
     * Checks the getUserNameOfCurrentAuthenticatedUser method.
     */
    @Test
    public void testGetLoggedInUser() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("GUEST",
                "USERNAME", AuthorityUtils.createAuthorityList("ADMIN")));
        String userName = AuthenticationUtil.getLoggedInUser();

        assertEquals("GUEST", userName);

    }

    /**
     * Checks if the currently authenticated user is an admin.
     */
   @Test
    public void testIsAuthenticatedUserAnAdmin() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("GUEST",
                "USERNAME", AuthorityUtils.createAuthorityList("ADMIN")));
        boolean expected = true;
        boolean actual = AuthenticationUtil.isAuthenticatedUserAnAdmin();

        assertEquals(expected, actual);
    }

}
