package com.rishirajdhr.flagship.auth;

import com.rishirajdhr.flagship.auth.exceptions.InvalidCredentialsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles authentication exceptions and maps them to HTTP REST responses.
 */
@RestControllerAdvice
public class AuthExceptionAdvice {
  @ExceptionHandler(InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String invalidCredentialsHandler(InvalidCredentialsException ex) {
    return ex.getMessage();
  }
}
