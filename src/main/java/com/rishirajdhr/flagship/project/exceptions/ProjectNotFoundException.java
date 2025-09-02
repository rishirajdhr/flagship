package com.rishirajdhr.flagship.project.exceptions;

/**
 * Represents an exception when a project could not be found.
 */
public class ProjectNotFoundException extends RuntimeException {
  /**
   * Create an exception when no project with a given ID could be found.
   *
   * @param id the project ID
   */
  public ProjectNotFoundException(Long id) {
    super("Could not find a project with ID: " + id);
  }
}
