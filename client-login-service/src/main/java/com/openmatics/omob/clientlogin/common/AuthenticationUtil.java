package com.openmatics.omob.clientlogin.common;

import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * AuthenticationUtil is a Utility class to check if authenticated or anonymous
 * user and Role of Authenticated user.
 *
 * @author RR00503779 (rr00503779@techmahindra.com)
 *
 */
public final class AuthenticationUtil {

    /**
     * Constant created for Admin role which can be used throughout the
     * application.
     */
    public static final String ADMIN = "ADMIN";

    /**
     * Constant created for User role which can be used throughout the
     * application.
     */
     public static final String USER = "USER";

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private AuthenticationUtil() {

    }

    /**
     * Returns the authentication details of the user.
     *
     * @return authentication details
     */
    public static Authentication getAuthentication() {

       return SecurityContextHolder.getContext().getAuthentication();

    }

    /**
     * Gets the userName of the currently authenticated user.
     *
     * @throws IllegalStateException if user is Anonymous User.
     * @return userName of the currently authenticated user.
     */
    public static String getLoggedInUser() {

       Authentication authentication = getAuthentication();

       if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
             return authentication.getName();
        } else {
            throw new IllegalStateException("The user is an anonymous user.");
        }
    }

    /**
     * Checks if the currently authenticated user is an admin.
     *
     * * @return true if logged-in user is an admin or else returns false."
     */
    public static boolean isAuthenticatedUserAnAdmin() {

       Authentication authentication = getAuthentication();

       Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();

       if (grantedAuthorities != null) {
            for (GrantedAuthority auth : grantedAuthorities) {
                if (StringUtils.equalsIgnoreCase(ADMIN, auth.getAuthority())) {
                     return true;
                }
            }
        }
       return false;
    }

}
