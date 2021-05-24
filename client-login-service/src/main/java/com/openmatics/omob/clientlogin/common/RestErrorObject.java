package com.openmatics.omob.clientlogin.common;


import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * When error on server occurs, response contains json object which describes exception.
 * This class represents this error object and it is used for instantiate Exception on client side
 *
 * @author dkacetl
 */
public class RestErrorObject implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * status of the response.
   */
  private int status;

  /**
   * error given by response Object.
   */
  private String error;
  /**
   *  message thrown by exception.
   */
  private String message;

  /**
   * timeStamp stored when response occured.
   */
  private DateTime timeStamp;

  /**
   * Exception given by response object.
   */
  private String exception;


  @SuppressWarnings("checkstyle:javadocmethod")
  public DateTime getTimeStamp() {
    return timeStamp;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setTimeStamp(DateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public int getStatus() {
    return status;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setStatus(int status) {
    this.status = status;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public String getError() {
    return error;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setError(String error) {
    this.error = error;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public String getException() {
    return exception;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setException(String exception) {
    this.exception = exception;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public String getMessage() {
    return message;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setMessage(String message) {
    this.message = message;
  }


}
