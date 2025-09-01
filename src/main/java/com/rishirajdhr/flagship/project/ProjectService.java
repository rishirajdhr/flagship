package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.auth.AppUser;
import com.rishirajdhr.flagship.auth.AppUserProvider;
import com.rishirajdhr.flagship.auth.exceptions.UnauthenticatedException;

import org.springframework.stereotype.Service;

/**
 * Encapsulates business logic for projects.
 */
@Service
public class ProjectService {
  private final ProjectRepository projectRepository;
  private final AppUserProvider appUserProvider;

  /**
   * Create a service to access and modify {@link Project} entities.
   *
   * @param projectRepository the repository that provides database access to project entities
   * @param appUserProvider the provider that supplies information about the logged-in user
   */
  public ProjectService(ProjectRepository projectRepository, AppUserProvider appUserProvider) {
    this.projectRepository = projectRepository;
    this.appUserProvider = appUserProvider;
  }

  /**
   * Create a new project.
   *
   * @param name the name of the project
   * @param description the description of the project
   * @return the newly created project
   */
  public Project createProject(String name, String description) {
    AppUser owner = appUserProvider.getLoggedInAppUser();
    if (owner == null) throw new UnauthenticatedException();

    Project project = new Project(name, description, owner);
    return projectRepository.save(project);
  }
}
