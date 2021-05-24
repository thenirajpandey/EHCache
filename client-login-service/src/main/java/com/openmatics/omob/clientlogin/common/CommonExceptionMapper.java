package com.openmatics.omob.clientlogin.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Common exception mapper for handling all exception.
 */
@Component
public class CommonExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

  /**
   * Initiate LOGGER for CommonExceptionMapper.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionMapper.class);

  public Response toResponse(Throwable throwable) {
    LOGGER.error(throwable.getMessage(), throwable);

    Response.StatusType type = getStatusType(throwable);

    RestErrorObject error = new RestErrorObject();
    error.setStatus(type.getStatusCode());
    error.setError(type.getReasonPhrase());
    error.setException(throwable.getClass().getName());
    error.setMessage(throwable.getLocalizedMessage());
    error.setTimeStamp(new DateTime());


    return Response.status(error.getStatus())
        .entity(error)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  /**
   * Method to update status type of exception.
   * @param ex throwable exception variable
   * @return response status type
   */
  private Response.StatusType getStatusType(Throwable ex) {
    if (ex instanceof WebApplicationException) {
      return ((WebApplicationException) ex).getResponse().getStatusInfo();
    } else {
      return Response.Status.INTERNAL_SERVER_ERROR;
    }
  }


}

