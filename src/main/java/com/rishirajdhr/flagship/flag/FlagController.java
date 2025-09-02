package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.auth.AppUser;
import com.rishirajdhr.flagship.auth.AppUserProvider;
import com.rishirajdhr.flagship.auth.exceptions.UnauthenticatedException;
import com.rishirajdhr.flagship.auth.exceptions.UnauthorizedException;
import com.rishirajdhr.flagship.flag.exceptions.FlagNotFoundException;
import com.rishirajdhr.flagship.project.Project;
import com.rishirajdhr.flagship.project.ProjectService;
import com.rishirajdhr.flagship.project.exceptions.ProjectNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

/**
 * Exposes REST endpoints to access {@link Flag} entities.
 */
@RestController
@RequestMapping("projects/{projectId}/flags")
public class FlagController {
  private final FlagService flagService;
  private final ProjectService projectService;
  private final AppUserProvider appUserProvider;

  /**
   * Create a new Flag controller.
   *
   * @param flagService the {@link Flag} business logic service
   * @param projectService the {@link Project} business logic service
   * @param appUserProvider the provider that supplies information about the logged-in user
   */
  public FlagController(FlagService flagService,
                        ProjectService projectService, AppUserProvider appUserProvider) {
    this.flagService = flagService;
    this.projectService = projectService;
    this.appUserProvider = appUserProvider;
  }

  /**
   * Create a new feature flag for a project
   *
   * @param projectId the ID of the project
   * @param newFlag the data for the new flag to be created
   * @return the newly created flag
   */
  @PostMapping
  public Flag createProjectFlag(@PathVariable Long projectId, @RequestBody NewFlag newFlag) {
    Project project = getAuthorizedProject(projectId);
    return flagService.createProjectFlag(
        newFlag.name(), newFlag.description(), newFlag.enabled(), project);
  }

  /**
   * Get all the feature flags for a project.
   *
   * @return a list of the project feature flags
   */
  @GetMapping
  public List<Flag> getAllFlagsForProject(@PathVariable Long projectId) {
    Project project = getAuthorizedProject(projectId);
    return flagService.getAllFlagsForProject(project);
  }

  /**
   * Get a feature flag for a project by its ID.
   *
   * @param flagId the ID of the flag
   * @param projectId the ID of the project
   * @return the feature flag
   */
  @GetMapping("/{flagId}")
  public Flag getProjectFlagById(@PathVariable Long flagId, @PathVariable Long projectId) {
    Project project = getAuthorizedProject(projectId);
    return flagService.getProjectFlagById(flagId, project).orElseThrow(() -> new FlagNotFoundException(flagId));
  }

  /**
   * Update a feature flag for a project by its ID.
   *
   * @param flagId the ID of the flag
   * @param projectId the ID of the project
   * @param updateFlag the payload with information to be updated for the flag
   * @return the updated feature flag
   */
  @PutMapping("/{flagId}")
  public Flag updateProjectFlagById(@PathVariable Long flagId, @PathVariable Long projectId,
                                    @RequestBody @Valid UpdateFlag updateFlag) {
    Project project = getAuthorizedProject(projectId);
    return flagService.updateProjectFlagById(flagId, project, updateFlag);
  }

  /**
   * Delete a feature flag for a project by its ID.
   *
   * @param flagId the ID of the flag
   * @param projectId the ID of the project
   * @return the deleted feature flag
   */
  @DeleteMapping("/{flagId}")
  public Flag deleteProjectFlagById(@PathVariable Long flagId, @PathVariable Long projectId) {
    Project project = getAuthorizedProject(projectId);
    return flagService.deleteProjectFlagById(flagId, project);
  }

  /**
   * Evaluate the state of a feature flag for a project by its name.
   *
   * @param flagName the name of the flag
   * @param projectId the ID of the project
   * @return the evaluated flag state
   */
  @PostMapping("/{flagName}/evaluate")
  public FlagState evaluateFlag(@PathVariable String flagName, @PathVariable Long projectId) {
    Project project = getAuthorizedProject(projectId);
    Optional<Flag> result = flagService.getProjectFlagByName(flagName, project);
    if (result.isEmpty()) throw new FlagNotFoundException(flagName);

    Flag flag = result.get();
    return flagService.createFlagState(flag);
  }

  /**
   * Get the project for the current route if it exists and if the authenticated user is authorized
   * to access it.
   *
   * @param projectId the project ID
   * @return the authorized project
   * @throws ProjectNotFoundException if no project is found with the given ID
   * @throws UnauthenticatedException if there is no authenticated user
   * @throws UnauthorizedException if the authenticated user is not authorized to access the project
   */
  private Project getAuthorizedProject(Long projectId)
      throws ProjectNotFoundException, UnauthenticatedException, UnauthorizedException {
    AppUser appUser = appUserProvider.getLoggedInAppUser();
    if (appUser == null) throw new UnauthenticatedException();

    Optional<Project> result = projectService.getProjectById(projectId);
    if (result.isEmpty()) throw new ProjectNotFoundException(projectId);

    Project project = result.get();
    AppUser owner = project.getOwner();
    if (!owner.equals(appUser)) {
      throw new UnauthorizedException("User is not authorized to access project");
    }

    return project;
  }
}
