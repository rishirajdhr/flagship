package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.auth.AppUser;
import com.rishirajdhr.flagship.project.exceptions.DuplicateProjectException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Encapsulates business logic for projects.
 */
@Service
public class ProjectService {
  private final ProjectRepository projectRepository;

  /**
   * Create a service to access and modify {@link Project} entities.
   *
   * @param projectRepository the repository that provides database access to project entities
   */
  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  /**
   * Create a new project for a user.
   *
   * @param name the name of the project
   * @param description the description of the project
   * @param owner the owner of the project.
   * @return the newly created project
   * @throws DuplicateProjectException if the user has an existing project with the same name
   */
  public Project createProject(String name, String description, AppUser owner) throws DuplicateProjectException {
    Project project = new Project(name, description, owner);
    try {
      return projectRepository.save(project);
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateProjectException(name);
    }
  }

  /**
   * Get the projects owned by a user.
   *
   * @param owner the owner of the projects
   * @return a list of the owner's projects
   */
  public List<Project> getAllProjectsForUser(AppUser owner) {
    return projectRepository.findAllByOwner(owner);
  }

  /**
   * Get a project by its ID.
   *
   * @param projectId the project ID
   * @return an {@link Optional} containing the project if found
   */
  public Optional<Project> getProjectById(Long projectId) {
    return projectRepository.findById(projectId);
  }
}
