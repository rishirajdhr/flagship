package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.project.dto.NewProjectRequest;
import com.rishirajdhr.flagship.project.dto.ProjectResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Exposes REST endpoints for projects.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
  private final ProjectService projectService;

  /**
   * Create a new controller to intercept project requests.
   *
   * @param projectService the service with utilities to work with projects
   */
  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  /**
   * Create a new project
   * @param newProjectRequest the payload with the information for the new project
   * @return the newly created project
   */
  @PostMapping
  public ProjectResponse createProject(@RequestBody @Valid NewProjectRequest newProjectRequest) {
    Project project = projectService.createProject(newProjectRequest.name(),
                                                   newProjectRequest.description());
    return ProjectResponse.fromProject(project);
  }
}
