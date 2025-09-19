package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.flag.exceptions.FlagNotFoundException;
import com.rishirajdhr.flagship.project.Project;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Encapsulates business logic for feature flags.
 */
@Service
public class FlagService {
  private final FlagRepository flagRepository;

  /**
   * Create a new service to access and modify feature flags.
   *
   * @param flagRepository the repository that provides database access to {@link Flag} entities
   */
  public FlagService(FlagRepository flagRepository) {
    this.flagRepository = flagRepository;
  }

  /**
   * Create a new feature flag for a project.
   *
   * @param key the key of the feature flag
   * @param name the name of the feature flag
   * @param description the description of the feature flag
   * @param enabled the feature flag status - {@code} true if enabled, {@code false} otherwise
   * @param project the project to create the flag for
   * @return the newly created feature flag
   */
  public Flag createProjectFlag(String key, String name, String description, boolean enabled,
                                Project project) {
    Flag flag = new Flag(key, name, description, enabled, project);
    return flagRepository.save(flag);
  }

  /**
   * Get all the feature flags for a project.
   *
   * @param project the project to get the flags for
   * @return a list of the project feature flags
   */
  public List<Flag> getAllFlagsForProject(Project project) {
    return flagRepository.findFlagsByProject(project);
  }

  /**
   * Get a feature flag for a project by its key.
   *
   * @param key the key of the flag
   * @param project the project of the flag
   * @return an {@link Optional} containing the flag if it exists
   */
  public Optional<Flag> getProjectFlagByKey(String key, Project project) {
    return flagRepository.findFlagByKeyAndProject(key, project);
  }

  /**
   * Get a feature flag for a project by its ID.
   *
   * @param flagId the ID of the flag
   * @param project the project of the flag
   * @return an {@link Optional} containing the flag if it exists
   */
  public Optional<Flag> getProjectFlagById(Long flagId, Project project) {
    return flagRepository.findFlagByIdAndProject(flagId, project);
  }

  /**
   * Update an existing feature flag by its ID.
   *
   * @param flagId the ID of the flag
   * @param project the project of the flag
   * @param updateFlag the {@link UpdateFlag} payload with the updated data
   * @return the updated feature flag
   * @throws FlagNotFoundException if no flag exists with the given ID
   */
  public Flag updateProjectFlagById(Long flagId, Project project, UpdateFlag updateFlag) throws FlagNotFoundException {
    Flag flag =
        getProjectFlagById(flagId, project).orElseThrow(() -> new FlagNotFoundException(flagId));

    if (updateFlag.description() != null) {
      flag.setDescription(updateFlag.description());
    }

    if (updateFlag.enabled() != null) {
      flag.setEnabled(updateFlag.enabled());
    }

    return flagRepository.save(flag);
  }

  /**
   * Delete a feature flag by its ID.
   *
   * @param flagId the ID of the flag
   * @param project the project of the flag
   * @return the deleted feature flag
   * @throws FlagNotFoundException if no flag is found with the given ID
   */
  public Flag deleteProjectFlagById(Long flagId, Project project) throws FlagNotFoundException {
    Flag flag =
        getProjectFlagById(flagId, project).orElseThrow(() -> new FlagNotFoundException(flagId));
    flagRepository.delete(flag);
    return flag;
  }

  /**
   * Evaluate a feature flag's state.
   *
   * @param flag the flag to be evaluated
   * @return the evaluated flag state
   */
  public FlagState createFlagState(Flag flag) {
    return new FlagState(flag.getName(), flag.isEnabled());
  }
}
