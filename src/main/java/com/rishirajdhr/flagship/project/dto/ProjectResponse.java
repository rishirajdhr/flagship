package com.rishirajdhr.flagship.project.dto;

import java.time.Instant;

import com.rishirajdhr.flagship.project.Project;

/**
 * Represents a response for a project request.
 *
 * @param name the project name
 * @param description the project description
 * @param owner the project owner's username
 * @param id the project ID
 * @param createdAt the timestamp when the project was created
 * @param updatedAt the timestamp when the project was last modified
 */
public record ProjectResponse(
    String name, String description, String owner, Long id, Instant createdAt, Instant updatedAt) {
  /**
   * Create a project response for a project.
   *
   * @param project the project to create a response for
   * @return the created project response
   */
  public static ProjectResponse fromProject(Project project) {
    return new ProjectResponse(
        project.getName(), project.getDescription(),
        project.getOwner().getUsername(), project.getId(),
        project.getCreatedAt(), project.getUpdatedAt());
  }
}
