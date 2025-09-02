package com.rishirajdhr.flagship.project.exceptions;

/**
 * Represents an exception when a user tries to create a project with the same name as one of their
 * existing projects.
 */
public class DuplicateProjectException extends RuntimeException {
  /**
   * Create an exception when a user tries to create a project with the same name as one of their
   * existing projects.
   *
   * @param projectName the name of the existing project
   */
  public DuplicateProjectException(String projectName) {
    super("User already has a project with name: " + projectName);
  }
}
