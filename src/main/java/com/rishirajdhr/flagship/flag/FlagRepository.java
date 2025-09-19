package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.project.Project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Represents a repository to access and modify {@link Flag} entities.
 */
public interface FlagRepository extends JpaRepository<Flag, Long> {

  /**
   * Get a feature flag within a project by its key.
   *
   * @param key the key of the flag
   * @param project the project of the flag
   * @return an {@link Optional} containing the flag if found
   */
  Optional<Flag> findFlagByKeyAndProject(String key, Project project);

  /**
   * Get a feature flag within a project by its ID.
   *
   * @param id the ID of the flag
   * @param project the project of the flag
   * @return an {@link Optional} containing the flag if found
   */
  Optional<Flag> findFlagByIdAndProject(Long id, Project project);

  /**
   * Get the feature flags for a project.
   *
   * @param project the parent project of the flags
   * @return a list of the project flags
   */
  List<Flag> findFlagsByProject(Project project);
}
