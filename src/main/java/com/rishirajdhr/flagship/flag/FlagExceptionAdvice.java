package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.flag.exceptions.FlagNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions when accessing {@link Flag} entities and maps them to HTTP REST responses.
 */
@RestControllerAdvice
public class FlagExceptionAdvice {
  @ExceptionHandler(FlagNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String flagNotFoundHandler(FlagNotFoundException ex) {
    return ex.getMessage();
  }
}
