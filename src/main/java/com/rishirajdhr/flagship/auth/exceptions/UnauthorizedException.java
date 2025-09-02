package com.rishirajdhr.flagship.auth.exceptions;

/**
 * Represents an exception when a request is not authorized.
 */
public class UnauthorizedException extends RuntimeException {

  /**
   * Create an exception for an unauthorized request.
   */
  public UnauthorizedException(String message) {
    super(message);
  }
}
