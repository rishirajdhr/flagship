package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.auth.AppUser;
import com.rishirajdhr.flagship.auth.AppUserProvider;
import com.rishirajdhr.flagship.auth.exceptions.UnauthenticatedException;
import com.rishirajdhr.flagship.project.dto.NewProjectRequest;
import com.rishirajdhr.flagship.project.dto.ProjectResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;

/**
 * Exposes REST endpoints for projects.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
  private final ProjectService projectService;
  private final AppUserProvider appUserProvider;

  /**
   * Create a new controller to intercept project requests.
   *
   * @param projectService the service with utilities to work with projects
   * @param appUserProvider the provider that supplies information about the logged-in user
   */
  public ProjectController(ProjectService projectService, AppUserProvider appUserProvider) {
    this.projectService = projectService;
    this.appUserProvider = appUserProvider;
  }

  /**
   * Create a new project for the logged-in user.
   *
   * @param newProjectRequest the payload with the information for the new project
   * @return the newly created project
   * @throws UnauthenticatedException if there is no authenticated user
   */
  @PostMapping
  public ProjectResponse createProjectForUser(@RequestBody @Valid NewProjectRequest newProjectRequest) throws UnauthenticatedException {
    AppUser owner = appUserProvider.getLoggedInAppUser();
    if (owner == null) throw new UnauthenticatedException();

    Project project = projectService.createProject(newProjectRequest.name(),
                                                   newProjectRequest.description(), owner);
    return ProjectResponse.fromProject(project);
  }

  /**
   * Get all the projects for the logged-in user.
   *
   * @return a list of all the user's projects
   * @throws UnauthenticatedException if there is no authenticated user
   */
  @GetMapping
  public List<ProjectResponse> getAllProjectsForUser() throws UnauthenticatedException {
    AppUser owner = appUserProvider.getLoggedInAppUser();
    if (owner == null) throw new UnauthenticatedException();

    return projectService.getAllProjectsForUser(owner).stream()
        .map(ProjectResponse::fromProject)
        .toList();
  }
}
