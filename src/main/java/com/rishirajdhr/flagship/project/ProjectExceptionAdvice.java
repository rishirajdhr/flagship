package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.project.exceptions.DuplicateProjectException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles project exceptions and maps them to HTTP REST responses.
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {

  @ExceptionHandler(DuplicateProjectException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String duplicateProjectHandler(DuplicateProjectException ex) {
    return ex.getMessage();
  }
}
