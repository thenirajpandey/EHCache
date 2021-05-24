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
package com.openmatics.omob.clientlogin.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

/**
 * This class provides Exception Mapper for HTTP error 404 Unauthorized.
 * @author Kather Basha
 *
 */
@Provider
public class AccessDeniedMapper implements ExceptionMapper<AccessDeniedException> {

   /**
    * @see javax.ws.rs.ext.ExceptionMapper<E>#toResponse(org.springframework.security.access.AccessDeniedException)
    */
    @Override
    public Response toResponse(AccessDeniedException e) {
        return Response.status(HttpStatus.FORBIDDEN.value())
                .build();
    }

}
