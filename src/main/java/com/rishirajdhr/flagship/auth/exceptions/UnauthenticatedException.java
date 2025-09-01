package com.rishirajdhr.flagship.auth.exceptions;

/**
 * Represents an exception when a request is not authenticated.
 */
public class UnauthenticatedException extends RuntimeException {
  private static final String message = "Authentication required";

  /**
   * Create an exception for an unauthenticated request.
   */
  public UnauthenticatedException() {
    super(message);
  }
}
